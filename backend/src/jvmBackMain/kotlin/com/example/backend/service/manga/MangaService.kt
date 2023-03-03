package com.example.backend.service.manga

import com.example.backend.jpa.manga.*
import com.example.backend.models.ChaptersHelper
import com.example.backend.models.MangaLightJson
import com.example.backend.models.PercentageList
import com.example.backend.models.ServiceResponse
import com.example.backend.repository.manga.MangaChaptersRepository
import com.example.backend.repository.manga.MangaGenreRepository
import com.example.backend.repository.manga.MangaRepository
import com.example.backend.repository.manga.MangaRepositoryImpl
import com.example.backend.service.image.ImageService
import com.example.backend.util.toPage
import com.example.common.models.mangaResponse.chapters.ChaptersLight
import com.example.common.models.mangaResponse.detail.GenresDetail
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.detail.TypesDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.google.gson.Gson
import it.skrape.core.document
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.eachHref
import it.skrape.selects.eachText
import it.skrape.selects.html5.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import kotlinx.serialization.json.Json
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.*
import org.springframework.data.jpa.domain.JpaSort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter
import javax.imageio.stream.ImageOutputStream


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

    fun listToMangaLight(
        manga: List<MangaTable>
    ): List<MangaLight> {
        val mangaLight = mutableListOf<MangaLight>()
        manga.forEach {
            mangaLight.add(
                MangaLight(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                )
            )
        }
        return mangaLight
    }

    fun mangaLightSuccess(
        mangaLight: List<MangaLight>
    ): ServiceResponse<MangaLight> {
        return ServiceResponse(
            data = mangaLight,
            message = "Success",
            status = HttpStatus.OK
        )
    }

    override fun getSimilarManga(
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int,
        id: String
    ): ServiceResponse<MangaLight>{
        println("Manga param = $pageNum | $pageSize | $id")
        val v = mangaRepository.findById(id).get()
        val b = mutableListOf<MangaGenre>()
        v.genres.forEach {
            if(b.size != 4) {
                b.add(mangaGenreRepository.findById(it.id).get())
            }
        }
        val t = mutableListOf<MangaTable>()
        val maxPercent = mutableListOf<PercentageList>()
        val temp = mangaRepository.findMGenres(pageable = PageRequest.of(pageNum, Short.MAX_VALUE.toInt()), status = null)
        temp.forEachIndexed { index, manga ->
            if(manga != v) {
                val firstSet = HashSet(manga.genres)
                val secondSet = HashSet(b)
                firstSet.retainAll(secondSet)
                maxPercent.add(PercentageList(firstSet.size, index))
            }
        }
        maxPercent.sortByDescending { it.size }
        maxPercent.forEach {
            if(it.size == b.size || it.size > 2) t.add(temp[it.index])
        }
        val p = PageRequest.of(pageNum, pageSize)
        return if(t.size > 0){
            mangaLightSuccess(listToMangaLight(t.toPage(p).content))
        } else {
            mangaLightSuccess(listOf(MangaLight()))
        }
    }


    override fun getAllManga(
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int,
        order: String?,
        genres: List<String>?,
        status: String?,
        searchQuery: String?
    ): ServiceResponse<MangaLight> {
        println("genres = $genres")
        println("Manga param = $pageNum | $pageSize | $order | ${genres?.size} | $status")
        val sort = when (order) {
            "popular" -> Sort.by(
                Sort.Order(Sort.Direction.DESC, "views"),
                Sort.Order(Sort.Direction.DESC, "countRate")
            )
            "random" -> JpaSort.unsafe("random()")

            "views" -> Sort.by(
                Sort.Order(Sort.Direction.DESC, "views")
            )

            else -> null
        }
        val sm = Short.MAX_VALUE.toInt()
        val pageable: Pageable = when {
            genres?.isNotEmpty() == true && sort != null -> {
                PageRequest.of(pageNum, sm, sort)
            }
            genres?.isNotEmpty() == true && sort == null -> {
                PageRequest.of(pageNum, sm)
            }
            sort != null -> {
                PageRequest.of(pageNum, pageSize, sort)
            }
            else -> PageRequest.of(pageNum, pageSize)
        }

        val b = mutableListOf<MangaGenre>()
        if(genres?.isNotEmpty() == true){
            genres.map { it.replace("[","").replace("]","") }.forEach {
                b.add(mangaGenreRepository.findById(it).get())
            }
            val t = mutableListOf<MangaTable>()
            val maxPercent = mutableListOf<PercentageList>()
            val temp = mangaRepository.findMGenres(pageable = pageable, status = status)
            temp.forEachIndexed { index, manga ->
                val firstSet = HashSet(manga.genres)
                val secondSet = HashSet(b)
                firstSet.retainAll(secondSet)
                maxPercent.add(PercentageList(firstSet.size, index))
            }
            maxPercent.sortByDescending { it.size }
            maxPercent.forEach {
                if(it.size > 0) t.add(temp[it.index])
            }
            val p = PageRequest.of(pageNum, pageSize)
            return if(t.size > 0){
                mangaLightSuccess(listToMangaLight(t.toPage(p).content))
            } else {
                mangaLightSuccess(listOf(MangaLight()))
            }
        }
        return mangaLightSuccess(listToMangaLight(mangaRepository.findManga(pageable = pageable, status = status, searchQuery = searchQuery)))
    }

    override fun getMangaChapters(id: String, pageNum: Int, pageSize: Int): ServiceResponse<ChaptersLight> {
        return try {
            val manga = mangaRepository.findById(id).get()
            try {
                val temp = mutableListOf<Pair<ChaptersHelper, MangaChapters>>()
                manga.chapters.forEach {
                    val tom = it.title.replace("Том ", "").split('.')[0]
                    var chapter =
                        it.title.replace("Глава", "").split("-")[0].replace("[^0-9.]".toRegex(), "").drop(tom.length)
                    val numbers = it.title.replace("[^0-9]".toRegex(), "")
                    if (chapter[0] == '.') {
                        chapter = chapter.drop(1)
                    }
                    val part = if (numbers.length - (chapter.length + tom.length) > 0) {
                        numbers.takeLast(numbers.length - (chapter.length + tom.length)).toInt()
                    } else 0
                    temp.add(
                        Pair(
                            ChaptersHelper(tom = tom.toInt(), chapter = chapter.toDouble(), part = part),
                            it
                        )
                    )
                }
                val sort = temp.sortedWith(
                    compareBy<Pair<ChaptersHelper, MangaChapters>> { false }
                        .thenBy { it.first.chapter }
                        .thenBy { it.first.tom }
                )
                val chapters = mutableListOf<MangaChapters>()
                sort.forEach {
                    chapters.add(it.second)
                }
                val chaptersLight = mutableListOf<ChaptersLight>()
                chaptersLight.add(
                    0, ChaptersLight(
                        title = chapters.last().title,
                        url = "${chapters.last().urlCode}",
                        date = chapters.last().date,
                        id = chapters.last().id
                    )
                )
                chaptersLight.removeLast()
                chapters.forEach { chapter ->
                    chaptersLight.add(
                        ChaptersLight(
                            id = chapter.id,
                            title = chapter.title,
                            url = "${chapter.urlCode}",
                            date = chapter.date
                        )
                    )
                }
                return ServiceResponse(
                    data = chaptersLight,
                    message = "Success",
                    status = HttpStatus.OK
                )
            } catch (e: Throwable) {
                ServiceResponse(
                    data = null,
                    message = e.message.toString(),
                    status = HttpStatus.BAD_REQUEST
                )
            }
        } catch (e: Throwable) {
            ServiceResponse(
                data = null,
                message = "Manga with id = $id not found",
                status = HttpStatus.NOT_FOUND
            )
        }
    }

    override fun getMangaLinked(id: String): ServiceResponse<MangaLight> {
        return try {
            val manga = mangaRepository.findById(id).get()
            try {
                val linkedLight = mutableListOf<MangaLight>()
                manga.linked.forEach { linked ->
                    val temp = mangaRepository.findById(linked).get()
                    linkedLight.add(
                        MangaLight(
                            id = temp.id,
                            title = temp.title,
                            image = temp.image
                        )
                    )
                }
                ServiceResponse(
                    data = linkedLight,
                    message = "Success",
                    status = HttpStatus.OK
                )
            } catch (e: Throwable) {
                ServiceResponse(
                    data = null,
                    message = e.message.toString(),
                    status = HttpStatus.BAD_REQUEST
                )
            }
        } catch (e: Throwable) {
            ServiceResponse(
                data = null,
                message = "Manga with id = $id not found",
                status = HttpStatus.NOT_FOUND
            )
        }
    }

    override fun getMangaById(id: String): ServiceResponse<MangaDetail> {
        return try {
            val manga = mangaRepository.findById(id).get()
            try {
                val genresDetail = mutableListOf<GenresDetail>()
                manga.genres.forEach { genre ->
                    genresDetail.add(
                        GenresDetail(
                            id = genre.id,
                            title = genre.title
                        )
                    )
                }
                val detail = toMangaDetails(
                    id = manga.id,
                    title = manga.title,
                    image = manga.image,
                    url = manga.url,
                    description = manga.description,
                    genres = genresDetail.toMutableSet(),
                    types = TypesDetail(
                        type = manga.types.type,
                        year = manga.types.year,
                        status = manga.types.status,
                        limitation = manga.types.limitation
                    ),
                    chaptersCount = manga.chaptersCount,
                    views = manga.views
                )
                ServiceResponse(
                    data = listOf(detail),
                    message = "Success",
                    status = HttpStatus.OK
                )
            } catch (e: Throwable) {
                ServiceResponse(
                    data = null,
                    message = e.message.toString(),
                    status = HttpStatus.BAD_REQUEST
                )
            }
        } catch (e: Throwable) {
            ServiceResponse(
                data = null,
                message = "Manga with id = $id not found",
                status = HttpStatus.NOT_FOUND
            )
        }
    }

    override fun addDataToDB(): MangaTable {
        var pageSize = 0
        val mangaUrls = mutableListOf<String>()
        pagesBoolean = false
        var pagesAvailable = false
        while (!pagesAvailable) {
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
                    println(i)
                    skrape(HttpFetcher) {
                        request {
                            url = "$mangaFull?page=$i"
                            timeout = 400_000
                        }
                        response {
                            document.a {
                                withClass = "d-block.rounded.fast-view-layer"
                                mangaUrls.addAll(findAll { return@findAll eachHref })
                            }
                        }
                    }
                }
                pagesAvailable = true
            } catch (e: Throwable) {
                pagesAvailable = false
            }
        }

        mangaUrls.forEach loop@{
            pagesBoolean = false
            while (!pagesBoolean) {
                try {
                    val json = Json { ignoreUnknownKeys = true }
                    val gson = Gson()
                    val manga = MangaTable()
                    val prevManga = mangaRepository.mangaByUrl(mangaSmall + it)

                    if (prevManga.isPresent && (prevManga.get().updateTime.plusDays(1).toLocalDate() != LocalDate.now())
                    ) {
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
                                this.div {
                                    withClass = "fs-2.text-muted.fw-medium.d-flex.align-items-center"
                                    val tempTypes =
                                        findAll { return@findAll eachText }.distinct().joinToString().split(" ")
                                    val tempYear = try {
                                        tempTypes[1].toInt()
                                    } catch (e: Throwable) {
                                        null
                                    }
                                    val tempStatusList =
                                        listOf(
                                            "онгоинг",
                                            "завершён",
                                            "анонс",
                                            "приостановлен",
                                            "выпуск прекращён"
                                        )
                                    val tempStatus = try {
                                        if (tempTypes[1] in tempStatusList && tempTypes.size > 1) {
                                            tempTypes[1]
                                        } else if (tempTypes[2] in tempStatusList && tempTypes.size > 2) {
                                            tempTypes[2]
                                        } else if (tempTypes[3] in tempStatusList && tempTypes.size > 3) {
                                            tempTypes[3]
                                        } else ""
                                    } catch (e: Throwable) {
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
                                    } catch (e: Throwable) {
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
                                    try {
                                        withClass = "tag.fw-medium"
                                        val tempGenres = findAll { return@findAll eachText }
                                        tempGenres.forEach { genre ->
                                            val id = UUID.randomUUID().toString()
                                            val genreIs = mangaGenreRepository.findByTitle(genre).isPresent
                                            if (genreIs) {
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
                                    } catch (e: Throwable) {
                                        return@a
                                    }
                                }
                                println("WTF = ${tempStart.image}")
                                println("WTF = ${IOUtils.toByteArray(URL(tempStart.image)).size}")

                                manga.image = imageService.save(compressImage(ImageIO.read(URL(tempStart.image))))
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
                                var countTemp = 0
                                this.span {
                                    withClass = "ms-1.badge.rounded-pill.bg-primary"
                                    countTemp = findFirst { text }[0].digitToInt()
                                }
                                if (countTemp > 0) {
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
                                        val first = tempChapterTitle.iterator()
                                        val second = tempChapterUrl.iterator()
                                        val third = tempChapterData.iterator()
                                        while (first.hasNext() && second.hasNext() && third.hasNext()) {
                                            val tempDate = third.next()
                                            val formatter =
                                                DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH)
                                            val tempUrl = mangaSmall + second.next()
                                            val tempTitle = first.next()
                                            val date = LocalDate.parse(tempDate, formatter)
                                            yield(
                                                MangaChapters(
                                                    date = date,
                                                    urlCode = tempUrl.removeRange(0, 25).toInt(),
                                                    title = tempTitle
                                                )
                                            )
                                        }
                                    }.toMutableSet()

                                    f.forEach { chapter ->
                                        val z = mangaChaptersRepository.save(
                                            MangaChapters(
                                                date = chapter.date,
                                                urlCode = chapter.urlCode,
                                                title = chapter.title
                                            )
                                        )
                                        manga.chapters.add(z)
                                    }
                                    manga.chaptersCount = manga.chapters.size
                                }
                            }
                        }
                    }

                    if (prevManga.isPresent) mangaRepository.deleteById(prevManga.get().id)

                    mangaRepository.save(manga)

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
                            elements.forEach { element ->
                                c.addAll(
                                    element.div {
                                        withClass = "text-line-clamp.mt-2"
                                        this.a {
                                            withClass = "fw-medium"
                                            findAll { return@findAll eachHref }
                                        }
                                    }
                                )
                                d.addAll(element.allElements.filter { filter -> filter.className == "mt-2" })
                            }

                            val tL = c.takeLast(d.size)

                            tL.forEach { url ->
                                val temp = mangaRepository.mangaByUrl(mangaSmall + url)
                                if (temp.isPresent) {
                                    manga.addMangaLinked(linkedTemp = temp.get().id)
                                } else {
                                    manga.addMangaLinked(
                                        linkedTemp = addLinkedManga(
                                            urlLink = url,
                                            prevUrlLink = it
                                        ).id
                                    )
                                }
                            }

                            if (prevManga.isPresent) mangaRepository.deleteById(prevManga.get().id)

                            mangaRepository.save(manga)
                        }
                    }

                    pagesBoolean = true
                } catch (e: Throwable) {
                    println("ERROR = ${e.cause}")
                    pagesBoolean = false
                }
            }
        }
        return MangaTable()
    }

    fun addLinkedManga(urlLink: String, prevUrlLink: String): MangaTable {
        pagesBooleanLinked = false
        val manga = MangaTable()

        while (!pagesBooleanLinked) {
            try {
                val json = Json { ignoreUnknownKeys = true }
                val gson = Gson()
                val prevManga = mangaRepository.mangaByUrl(mangaSmall + urlLink)

                if (prevManga.isPresent && (prevManga.get().updateTime.plusDays(1).toLocalDate() != LocalDate.now())
                ) {
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
                            this.div {
                                withClass = "fs-2.text-muted.fw-medium.d-flex.align-items-center"
                                val tempTypes =
                                    findAll { return@findAll eachText }.distinct().joinToString().split(" ")
                                val tempYear = try {
                                    tempTypes[1].toInt()
                                } catch (e: Throwable) {
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
                                } catch (e: Throwable) {
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
                                } catch (e: Throwable) {
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
                                    if (genreIs) {
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
                            manga.image = imageService.save(compressImage(ImageIO.read(URL(tempStart.image))))
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
                                            date = date,
                                            urlCode = tempUrl.removeRange(0, 25).toInt(),
                                            title = tempTitle
                                        )
                                    )
                                }
                            }.toMutableSet()

                            f.forEach {
                                val z = mangaChaptersRepository.save(
                                    MangaChapters(
                                        date = it.date,
                                        urlCode = it.urlCode,
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
                        elements.forEach { element ->
                            c.addAll(
                                element.div {
                                    withClass = "text-line-clamp.mt-2"
                                    this.a {
                                        withClass = "fw-medium"
                                        findAll { return@findAll eachHref }
                                    }
                                }
                            )
                            d.addAll(element.allElements.filter { filter -> filter.className == "mt-2" })
                        }

                        val tL = c.takeLast(d.size)

                        tL.forEach { url ->
                            val temp = mangaRepository.mangaByUrl(mangaSmall + url)
                            if (temp.isPresent) {
                                manga.addMangaLinked(linkedTemp = temp.get().id)
                            } else {
                                manga.addMangaLinked(
                                    linkedTemp = addLinkedManga(
                                        urlLink = url,
                                        prevUrlLink = urlLink
                                    ).id
                                )
                            }
                        }
                    }
                }

                if (prevManga.isPresent) {
                    mangaRepository.deleteById(prevManga.get().id)
                }

                mangaRepository.save(manga)

                pagesBooleanLinked = true
            } catch (e: Throwable) {
                println("ERROR = ${e.message}")
                pagesBooleanLinked = false
            }
        }
        return mangaRepository.findById(manga.id).get()
    }


    fun compressImage(image: BufferedImage): ByteArray {
        val convertedImg = BufferedImage(image.width, image.height, 6)
        convertedImg.graphics.drawImage(image, 0, 0, null)
        val ww = removeAlphaChannel(convertedImg)
        val outputStream = ByteArrayOutputStream()
        val bOutputStream = ByteArrayOutputStream()

        ImageIO.write(ww, "jpg", bOutputStream)
        println("WTF = ${bOutputStream.toByteArray().size}")
        val imageWriters: Iterator<ImageWriter> =
            ImageIO.getImageWritersByFormatName("jpg")
        val imageOutputStream: ImageOutputStream = ImageIO.createImageOutputStream(outputStream)
        val imageWriter: ImageWriter = imageWriters.next()
        imageWriter.output = imageOutputStream
        val imageWriteParam: ImageWriteParam = imageWriter.defaultWriteParam
        imageWriteParam.compressionMode = ImageWriteParam.MODE_EXPLICIT
        when(bOutputStream.toByteArray().size){
            in 300000..400000 -> imageWriteParam.compressionQuality = 0.3f
            in 200000..300000 -> imageWriteParam.compressionQuality = 0.5f
            in 100000..200000 -> imageWriteParam.compressionQuality = 0.6f
            in 0..100000 -> imageWriteParam.compressionQuality = 0.7f
            else -> imageWriteParam.compressionQuality = 0.09f
        }

        imageWriter.write(null, IIOImage(ww, null, null), imageWriteParam)
        val imageBytes = outputStream.toByteArray()
        println(imageBytes.size)
        return outputStream.toByteArray()
    }

    private fun removeAlphaChannel(img: BufferedImage): BufferedImage {
        if (!img.colorModel.hasAlpha()) {
            return img
        }
        val target = createImage(img.width, img.height, false)
        val g = target.createGraphics()
        g.fillRect(0, 0, img.width, img.height)
        g.drawImage(img, 0, 0, null)
        g.dispose()
        return target
    }

    private fun createImage(width: Int, height: Int, hasAlpha: Boolean): BufferedImage {
        return BufferedImage(width, height, if (hasAlpha) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB)
    }
}