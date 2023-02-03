package com.example.backend.service.manga

import com.example.backend.jpa.manga.MangaChapters
import com.example.backend.jpa.manga.MangaGenre
import com.example.backend.jpa.manga.MangaTable
import com.example.backend.jpa.manga.MangaTypes
import com.example.backend.models.MangaLightJson
import com.example.backend.repository.manga.MangaChaptersRepository
import com.example.backend.repository.manga.MangaGenreRepository
import com.example.backend.repository.manga.MangaRepository
import com.example.backend.repository.manga.MangaRepositoryImpl
import com.example.backend.service.image.ImageService
import com.example.common.models.mangaResponse.detail.GenresDetail
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.detail.TypesDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse
import com.google.gson.Gson
import it.skrape.core.document
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.eachHref
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.li
import it.skrape.selects.html5.script
import kotlinx.serialization.json.Json
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class MangaService: MangaRepositoryImpl {

    @Value("\${afa.app.manga_full}")
    lateinit var mangaFull: String

    @Value("\${afa.app.manga_small}")
    lateinit var mangaSmall: String

    @Autowired
    lateinit var mangaRepository: MangaRepository

    @Autowired
    lateinit var mangaChaptersRepository: MangaChaptersRepository

    @Autowired
    lateinit var mangaGenreRepository: MangaGenreRepository

    @Autowired
    lateinit var imageService: ImageService

    private var pagesBoolean = false

    private var pagesBooleanLinked = false


    fun toMangaDetails(
        id: String,
        title: String,
        image: String,
        url: String,
        description: String,
        genres: MutableSet<GenresDetail>,
        types: TypesDetail,
        chaptersCount: Int,
        views: Int,
    ): MangaDetail {
        return MangaDetail(
            id = id,
            title = title,
            image = image,
            url = url,
            description = description,
            genres = genres,
            types = types,
            chaptersCount = chaptersCount,
            views = views
        )
    }

    fun toMangaLight(
        id: String,
        title: String,
        image: String,
    ): MangaLight {
        return MangaLight(
            id = id,
            title = title,
            image = image,
        )
    }

    override fun getAllManga(): List<MangaLight> {
        val mangaLight = mutableListOf<MangaLight>()
        mangaRepository.findAll().forEach { manga ->
            mangaLight.add(
                toMangaLight(
                    id = manga.id,
                    title = manga.title,
                    image = manga.image,
                )
            )
        }
        println(mangaLight.size)
        return mangaLight
    }

    override fun getMangaById(id: String): ServiceResponse<MangaDetail> {
        val temp = mangaRepository.findMangaDetailById(id).get()
        println(temp)
//        val genresDetail = mutableListOf<GenresDetail>()
//        temp.genres.forEach { genre ->
//            genresDetail.add(
//                GenresDetail(
//                    id = genre.id,
//                    title = genre.title
//                )
//            )
//        }
//        val detail = toMangaDetails(
//            id = temp.id,
//            title = temp.title,
//            image = temp.image,
//            url = temp.url,
//            description = temp.description,
//            genres = genresDetail.toMutableSet(),
//            types = TypesDetail(
//                type = temp.types.type,
//                year = temp.types.year,
//                status = temp.types.status,
//                limitation = temp.types.limitation
//            ),
//            chaptersCount = temp.chaptersCount,
//            views = temp.views
//        )
        return try {
            ServiceResponse(
                data = listOf(MangaDetail()),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: Exception){
            ServiceResponse(
                data = null,
                message = e.message.toString(),
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    override fun addDataToDB(): MangaTable {
        var pageSize = 0

        pagesBoolean = false

        while (!pagesBoolean) {
            try {
                skrape(HttpFetcher) {
                    request {
                        url = mangaFull + 1
                    }
                    response {
                        document.li {
                            withClass = "page-item"
                            val pages = findAll { return@findAll eachText }
                            pageSize = pages[pages.lastIndex - 1].toInt()
                        }
                    }
                }

                for (i in 1 until pageSize) {

                    val mangaUrls = mutableListOf<String>()

                    skrape(HttpFetcher) {
                        request {
                            url = mangaFull + pageSize
                            timeout = 400_000
                        }
                        response {
                            document.a {
                                withClass = "d-block.rounded.fast-view-layer"
                                mangaUrls.addAll(findAll { return@findAll eachHref })
                            }
                        }
                    }

                    mangaUrls.forEach loop@ {
                        val json = Json { ignoreUnknownKeys = true }
                        val gson = Gson()
                        val manga = MangaTable()
                        val prevManga = mangaRepository.mangaByUrl(mangaSmall + it)

                        if (prevManga.isPresent && (prevManga.get().updateTime.plusDays(1).toLocalDate() != LocalDate.now())
                        ){
                            return@loop
                        }

                        skrape(HttpFetcher) {
                            request {
                                url = mangaSmall + it
                                timeout = 400_000
                            }
                            response {
                                htmlDocument {
                                    val schemaScript = script {
                                        withAttribute = "type" to "application/ld+json"
                                        findFirst { this }
                                    }
                                    val mangaJsonElement = json.parseToJsonElement(schemaScript.html)
                                    val tempStart =
                                        gson.fromJson(mangaJsonElement.toString(), MangaLightJson::class.java)
                                    manga.url = tempStart.url
                                    manga.title = tempStart.name
                                    manga.description = tempStart.description
                                    manga.image = tempStart.image
                                    this.div {
                                        withClass = "fs-2.text-muted.fw-medium.d-flex.align-items-center"
                                        val tempTypes =
                                            findAll { return@findAll eachText }.distinct().joinToString().split(" ")
                                        val tempYear = try {
                                            tempTypes[1].toInt()
                                        } catch (e: Exception) {
                                            null
                                        }
                                        val tempStatusList =
                                            listOf("онгоинг", "завершён", "анонс", "приостановлен", "выпуск прекращён")
                                        val tempStatus = try {
                                            if (tempTypes[1] in tempStatusList && tempTypes.size > 1) {
                                                tempTypes[1]
                                            } else if (tempTypes[2] in tempStatusList && tempTypes.size > 2) {
                                                tempTypes[2]
                                            } else if (tempTypes[3] in tempStatusList && tempTypes.size > 3) {
                                                tempTypes[3]
                                            } else ""
                                        } catch (e: Exception) {
                                            ""
                                        }
                                        val tempLimitationList = listOf("16+", "18+")
                                        val limitation = try {
                                            if (tempTypes[1] in tempLimitationList && tempTypes.size > 1) {
                                                tempTypes[1]
                                            } else if (tempTypes[2] in tempLimitationList && tempTypes.size > 2) {
                                                tempTypes[2]
                                            } else if (tempTypes[3] in tempLimitationList && tempTypes.size > 3) {
                                                tempTypes[3]
                                            } else ""
                                        } catch (e: Exception) {
                                            ""
                                        }

                                        manga.types = MangaTypes(
                                            type = tempTypes[0],
                                            year = tempYear,
                                            status = tempStatus,
                                            limitation = limitation
                                        )
                                    }
                                    this.a {
                                        withClass = "tag.fw-medium"
                                        val tempGenres = findAll { return@findAll eachText }
                                        tempGenres.forEach { genre ->
                                            val id = UUID.randomUUID().toString()
                                            val genreIs = mangaGenreRepository.findByTitle(genre).isPresent
                                            if(genreIs){
                                                val temp = mangaGenreRepository.findByTitle(genre).get()
                                                manga.addMangaGenre(
                                                    MangaGenre(id = temp.id, title = temp.title)
                                                )
                                            } else {
                                                mangaGenreRepository.save(
                                                    MangaGenre(id = id, title = genre)
                                                )
                                                manga.addMangaGenre(
                                                    MangaGenre(id = id, title = genre)
                                                )
                                            }
                                        }
                                    }
                                    manga.image = imageService.save(IOUtils.toByteArray(URL(tempStart.image)))
                                }
                            }
                        }

                        skrape(HttpFetcher) {
                            request {
                                url = mangaSmall + "${it}/like"
                                timeout = 400_000
                            }
                            response {
                                val b = mutableListOf<String>()
                                document.div {
                                    withClass = "detail-section-header"
                                    b.addAll(findAll { return@findAll eachText })
                                }
                                val elements =
                                    document.allElements.filter { it.className.contains("scroller-item me-3") }
                                val c = mutableListOf<String>()
                                val d = mutableListOf<DocElement>()

                                elements.forEach {
                                    c.addAll(
                                        it.div {
                                            withClass = "text-line-clamp.mt-2"
                                            this.a {
                                                withClass = "fw-medium"
                                                findAll { return@findAll eachHref }
                                            }
                                        }
                                    )
                                    d.addAll(it.allElements.filter { it.className == "mt-2" })
                                }
                                val tL = c.takeLast(d.size)

                                tL.forEach { url ->
                                    val temp = mangaRepository.mangaByUrl(mangaSmall + url)
                                    if(temp.isPresent){
                                        manga.addMangaLinked(linkedTemp = temp.get())
                                    } else {
                                        manga.addMangaLinked(linkedTemp = addLinkedManga(urlLink = url, prevUrlLink = it))
                                    }
                                }
                            }
                        }

                        skrape(HttpFetcher) {
                            request {
                                url = mangaSmall + it.replace("title", "chapters")
                                timeout = 400_000
                            }
                            response {
                                htmlDocument {
                                    val tempChapterData = mutableListOf<String>()
                                    val tempChapterUrl = mutableListOf<String>()
                                    val tempChapterTitle = mutableListOf<String>()

                                    this.div {
                                        withClass = "detail-chapter-date.ms-2.text-muted"
                                        tempChapterData.addAll(findAll { eachText })
                                    }

                                    this.a {
                                        withClass =
                                            "d-inline-flex.ms-2.fs-2.fw-medium.text-reset.min-w-0.flex-lg-grow-1"
                                        tempChapterTitle.addAll(findAll { eachText })
                                        println(tempChapterTitle)
                                        tempChapterUrl.addAll(findAll { eachHref })
                                        println(tempChapterUrl)
                                    }

                                    val f = sequence {
                                        val id = UUID.randomUUID().toString()
                                        val first = tempChapterTitle.iterator()
                                        val second = tempChapterUrl.iterator()
                                        val third = tempChapterData.iterator()
                                        while (first.hasNext() && second.hasNext() && third.hasNext()) {
                                            val tempDate = third.next()
                                            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH)
                                            val tempUrl = mangaSmall + second.next()
                                            val tempTitle = first.next()
                                            val date = LocalDate.parse(tempDate, formatter)
                                            yield(
                                                MangaChapters(
                                                    id = id,
                                                    date = date,
                                                    url = tempUrl,
                                                    title = tempTitle
                                                )
                                            )
                                        }
                                    }.toMutableSet()

                                    f.forEach { chapter ->
                                        val z =  mangaChaptersRepository.save(
                                            MangaChapters(
                                                date = chapter.date,
                                                url = chapter.url,
                                                title = chapter.title
                                            )
                                        )
                                        manga.chapters.add(z)
                                    }
                                    manga.chaptersCount = manga.chapters.size
                                }
                            }
                        }

                        if(prevManga.isPresent) mangaRepository.deleteById(prevManga.get().id)

                        mangaRepository.save(manga)
                    }
                }
                pagesBoolean = true
            } catch (e: Exception) {
                println("ERROR = ${e.message}")
                pagesBoolean = false
            }
        }

        return MangaTable()
    }

    fun addLinkedManga(urlLink: String, prevUrlLink: String): MangaTable {
        println("URL = $urlLink")
        pagesBooleanLinked = false
        val manga = MangaTable()

        while (!pagesBooleanLinked) {
            try {
                val json = Json { ignoreUnknownKeys = true }
                val gson = Gson()
                val prevManga = mangaRepository.mangaByUrl(mangaSmall + urlLink)

                if (prevManga.isPresent && (prevManga.get().updateTime.plusDays(1).toLocalDate() != LocalDate.now())
                ){
                    return prevManga.get()
                }

                skrape(HttpFetcher) {
                    request {
                        url = mangaSmall + urlLink
                        timeout = 400_000
                    }
                    response {
                        htmlDocument {
                            val schemaScript = script {
                                withAttribute = "type" to "application/ld+json"
                                findFirst { this }
                            }
                            val mangaJsonElement = json.parseToJsonElement(schemaScript.html)
                            val tempStart =
                                gson.fromJson(mangaJsonElement.toString(), MangaLightJson::class.java)
                            manga.url = tempStart.url
                            manga.title = tempStart.name
                            manga.description = tempStart.description
                            manga.image = tempStart.image
                            this.div {
                                withClass = "fs-2.text-muted.fw-medium.d-flex.align-items-center"
                                val tempTypes =
                                    findAll { return@findAll eachText }.distinct().joinToString().split(" ")
                                val tempYear = try {
                                    tempTypes[1].toInt()
                                } catch (e: Exception) {
                                    null
                                }
                                val tempStatusList =
                                    listOf("онгоинг", "завершён", "анонс", "приостановлен", "выпуск прекращён")
                                val tempStatus = try {
                                    if (tempTypes[1] in tempStatusList && tempTypes.size > 1) {
                                        tempTypes[1]
                                    } else if (tempTypes[2] in tempStatusList && tempTypes.size > 2) {
                                        tempTypes[2]
                                    } else if (tempTypes[3] in tempStatusList && tempTypes.size > 3) {
                                        tempTypes[3]
                                    } else ""
                                } catch (e: Exception) {
                                    ""
                                }
                                val tempLimitationList = listOf("16+", "18+")
                                val limitation = try {
                                    if (tempTypes[1] in tempLimitationList && tempTypes.size > 1) {
                                        tempTypes[1]
                                    } else if (tempTypes[2] in tempLimitationList && tempTypes.size > 2) {
                                        tempTypes[2]
                                    } else if (tempTypes[3] in tempLimitationList && tempTypes.size > 3) {
                                        tempTypes[3]
                                    } else ""
                                } catch (e: Exception) {
                                    ""
                                }

                                manga.types = MangaTypes(
                                    type = tempTypes[0],
                                    year = tempYear,
                                    status = tempStatus,
                                    limitation = limitation
                                )
                            }
                            this.a {
                                withClass = "tag.fw-medium"
                                val tempGenres = findAll { return@findAll eachText }
                                tempGenres.forEach { genre ->
                                    val id = UUID.randomUUID().toString()
                                    val genreIs = mangaGenreRepository.findByTitle(genre).isPresent
                                    if(genreIs){
                                        val temp = mangaGenreRepository.findByTitle(genre).get()
                                        manga.addMangaGenre(
                                            MangaGenre(id = temp.id, title = temp.title)
                                        )
                                    } else {
                                        mangaGenreRepository.save(
                                            MangaGenre(id = id, title = genre)
                                        )
                                        manga.addMangaGenre(
                                            MangaGenre(id = id, title = genre)
                                        )
                                    }
                                }
                            }
                            manga.image = imageService.save(IOUtils.toByteArray(URL(tempStart.image)))
                        }
                    }
                }

                skrape(HttpFetcher) {
                    request {
                        url = mangaSmall + "${urlLink}/like"
                        timeout = 400_000
                    }
                    response {
                        val b = mutableListOf<String>()
                        document.div {
                            withClass = "detail-section-header"
                            b.addAll(findAll { return@findAll eachText })
                        }
                        val elements =
                            document.allElements.filter { it.className.contains("scroller-item me-3") }
                        val c = mutableListOf<String>()
                        val d = mutableListOf<DocElement>()

                        elements.forEach {
                            c.addAll(
                                it.div {
                                    withClass = "text-line-clamp.mt-2"
                                    this.a {
                                        withClass = "fw-medium"
                                        findAll { return@findAll eachHref }
                                    }
                                }
                            )
                            d.addAll(it.allElements.filter { it.className == "mt-2" })
                        }
                        val tL = c.takeLast(d.size)

                        tL.forEach {
                            val temp = mangaRepository.mangaByUrl(mangaSmall + it)
                            if(temp.isPresent){
                                manga.addMangaLinked(linkedTemp = temp.get())
                            } else {
                                println("URLLINK = $it")
                                println("URLLINKDZXC = $urlLink")
                                if(it != prevUrlLink) manga.addMangaLinked(linkedTemp = addLinkedManga(prevUrlLink = urlLink, urlLink = it))
                            }
                        }
                    }
                }

                skrape(HttpFetcher) {
                    request {
                        url = mangaSmall + urlLink.replace("title", "chapters")
                        timeout = 400_000
                    }
                    response {
                        htmlDocument {
                            val tempChapterData = mutableListOf<String>()
                            val tempChapterUrl = mutableListOf<String>()
                            val tempChapterTitle = mutableListOf<String>()

                            this.div {
                                withClass = "detail-chapter-date.ms-2.text-muted"
                                tempChapterData.addAll(findAll { eachText })
                            }

                            this.a {
                                withClass =
                                    "d-inline-flex.ms-2.fs-2.fw-medium.text-reset.min-w-0.flex-lg-grow-1"
                                tempChapterTitle.addAll(findAll { eachText })
                                tempChapterUrl.addAll(findAll { eachHref })
                            }

                            val f = sequence {
                                val id = UUID.randomUUID().toString()
                                val first = tempChapterTitle.iterator()
                                val second = tempChapterUrl.iterator()
                                val third = tempChapterData.iterator()
                                while (first.hasNext() && second.hasNext() && third.hasNext()) {
                                    val tempDate = third.next()
                                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH)
                                    val tempUrl = mangaSmall + second.next()
                                    val tempTitle = first.next()
                                    val date = LocalDate.parse(tempDate, formatter)
                                    yield(
                                        MangaChapters(
                                            id = id,
                                            date = date,
                                            url = tempUrl,
                                            title = tempTitle
                                        )
                                    )
                                }
                            }.toMutableSet()

                            f.forEach {
                               val z =  mangaChaptersRepository.save(
                                    MangaChapters(
                                        date = it.date,
                                        url = it.url,
                                        title = it.title
                                    )
                               )
                                manga.chapters.add(z)
                            }
                            manga.chaptersCount = manga.chapters.size
                        }
                    }
                }
                if (prevManga.isPresent) {
                    mangaRepository.deleteById(prevManga.get().id)
                }

                mangaRepository.save(manga)

                pagesBooleanLinked = true
            } catch (e: Exception) {
                println("ERROR = ${e.message}")
                pagesBooleanLinked = false
            }
        }
        return mangaRepository.findById(manga.id).get()
    }

}