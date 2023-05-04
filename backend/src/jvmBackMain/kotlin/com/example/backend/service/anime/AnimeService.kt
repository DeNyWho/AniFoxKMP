package com.example.backend.service.anime


import com.example.backend.jpa.anime.*
import com.example.backend.models.ServiceResponse
import com.example.backend.models.animeParser.AnimeMediaParse
import com.example.backend.models.animeParser.AnimeResponse
import com.example.backend.models.animeParser.AnimeTempResponse
import com.example.backend.models.animeResponse.detail.AnimeDetail
import com.example.backend.models.animeResponse.light.AnimeLight
import com.example.backend.models.animeResponse.media.AnimeMediaResponse
import com.example.backend.repository.anime.*
import com.example.backend.util.translit
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.JpaSort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.IOException
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Service
class AnimeService: AnimeRepositoryImpl {

    @Value("\${anime.ko.token}")
    lateinit var animeToken: String

    @Autowired
    lateinit var animeStudiosRepository: AnimeStudiosRepository

    @Autowired
    lateinit var animeGenreRepository: AnimeGenreRepository

    @Autowired
    lateinit var animeTranslationRepository: AnimeTranslationRepository

    @Autowired
    lateinit var animeMediaRepository: AnimeMediaRepository

    @Autowired
    lateinit var animeRepository: AnimeRepository

    @PersistenceContext
    private lateinit var entityManager: EntityManager


    override fun getAnime(
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int,
        order: String?,
        genres: List<String>?,
        status: String?,
        searchQuery: String?,
        ratingMpa: String?,
        season: String?,
        minimalAge: Int?,
        type: String?,
        year: List<Int>?,
        translations: List<String>?
    ): ServiceResponse<AnimeLight> {
        val actualStatus = status?.ifEmpty { null }
        val actualSearch = searchQuery?.ifEmpty { null }

        val cacheKey = "$pageNum|$pageSize|$order|${genres?.size}|$status|${year?.size}|${translations?.size}"

        println("Anime param = $pageNum | $pageSize | $order | ${genres?.size} | $status | ${year?.size} | ${translations?.size}")
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
        val pageable: Pageable = when {
            sort != null -> {
                PageRequest.of(pageNum, pageSize, sort)
            }
            else -> PageRequest.of(pageNum, pageSize)
        }
        return animeLightSuccess(
            listToAnimeLight(
                findAnime(
                    pageable = pageable,
                    status = actualStatus,
                    searchQuery = actualSearch,
                    ratingMpa = ratingMpa,
                    season = season,
                    minimalAge = minimalAge,
                    type = type,
                    year = year,
                    genres = genres,
                    translationIds = translations
                )
            )
        )
    }

    fun findAnime(
        pageable: Pageable,
        status: String?,
        searchQuery: String?,
        ratingMpa: String?,
        season: String?,
        minimalAge: Int?,
        type: String?,
        year: List<Int>?,
        genres: List<String>?,
        translationIds: List<String>?
    ): List<AnimeTable> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<AnimeTable> = criteriaBuilder.createQuery(AnimeTable::class.java)
        val root: Root<AnimeTable> = criteriaQuery.from(AnimeTable::class.java)
        criteriaQuery.select(root)

        val predicates: MutableList<Predicate> = mutableListOf()
        if (!status.isNullOrEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get<String>("status"), status))
        }
        if (!ratingMpa.isNullOrEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get<String>("ratingMpa"), ratingMpa))
        }
        if (!season.isNullOrEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get<String>("season"), season))
        }
        if (!type.isNullOrEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get<String>("type"), type))
        }
        if (minimalAge != null) {
            predicates.add(criteriaBuilder.equal(root.get<Int>("minimalAge"), minimalAge))
        }
        if (!year.isNullOrEmpty()) {
            predicates.add(root.get<Int>("year").`in`(year))
        }
        if (!genres.isNullOrEmpty()) {
            val genresJoin = root.join<AnimeTable, AnimeGenreTable>("genres")
            val genrePredicates = mutableListOf<Predicate>()
            for (genre in genres) {
                genrePredicates.add(criteriaBuilder.equal(genresJoin.get<String>("id"), genre))
            }
            predicates.add(criteriaBuilder.or(*genrePredicates.toTypedArray()))
        }
        if (!searchQuery.isNullOrEmpty()) {
            val ilikeExpression: Expression<Boolean> = criteriaBuilder.like(
                criteriaBuilder.lower(root.get("title")),
                "%" + searchQuery.lowercase(Locale.getDefault()) + "%"
            )

            val exactMatchPredicate: Predicate = criteriaBuilder.equal(root.get<String>("title"), searchQuery)

            predicates.add(criteriaBuilder.or(ilikeExpression, exactMatchPredicate))
        }
        if (!translationIds.isNullOrEmpty()) {
            val translationJoin = root.join<AnimeTable, AnimeTranslationTable>("translation")
            val translationIdsPredicate = criteriaBuilder.isTrue(
                translationJoin.get<AnimeTranslationTable>("id").`in`(translationIds.mapNotNull { it.toIntOrNull() })
            )
            predicates.add(translationIdsPredicate)
        }

        if (predicates.isNotEmpty()) {
            criteriaQuery.where(*predicates.toTypedArray())
        }

        val query = entityManager.createQuery(criteriaQuery)
        query.firstResult = pageable.pageNumber * pageable.pageSize
        query.maxResults = pageable.pageSize

        return query.resultList
    }

    override fun getAnimeById(id: String): ServiceResponse<AnimeDetail> {
        return try {
            try {
                val anime = animeRepository.findDetails(id).get()
                return ServiceResponse(
                    data = listOf(animeToAnimeDetail(anime)),
                    message = "Success",
                    status = HttpStatus.OK
                )
            } catch (e: Exception) {
                ServiceResponse(
                    data = null,
                    message = "Anime with id = $id not found",
                    status = HttpStatus.NOT_FOUND
                )
            }
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    override fun getAnimeScreenshotsById(id: String): ServiceResponse<String> {
        return try {
            try {
                val criteriaBuilder = entityManager.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(String::class.java)
                val root = criteriaQuery.from(AnimeTable::class.java)
                val screenshotsPath: Join<AnimeTable, String> = root.joinList("screenshots")

                criteriaQuery.select(screenshotsPath)
                    .where(criteriaBuilder.equal(root.get<String>("url"), id))
                    .distinct(true)

                val query = entityManager.createQuery(criteriaQuery)

                ServiceResponse(
                    data = query.resultList,
                    message = "Success",
                    status = HttpStatus.OK
                )
            } catch (e: Exception) {
                ServiceResponse(
                    data = null,
                    message = "Anime with id = $id not found",
                    status = HttpStatus.NOT_FOUND
                )
            }
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    fun getMostCommonColor(image: BufferedImage): String {
        val brightestColors = getBrightestColors(image)
        val numColors = brightestColors.size

        if (numColors == 0) {
            return "#FFFFFF" // Белый цвет
        }

        val red = brightestColors.sumOf { it.red } / numColors
        val green = brightestColors.sumOf { it.green } / numColors
        val blue = brightestColors.sumOf { it.blue } / numColors

        return String.format("#%02X%02X%02X", red, green, blue)
    }

    fun getBrightestColors(image: BufferedImage): List<Color> {
        // Создаем объект Map для хранения цветов и их количества
        val colorCount = mutableMapOf<Int, Int>()

        // Перебираем каждый пиксель на изображении и сохраняем его цвет в списке
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                val pixel = image.getRGB(x, y)
                val red = (pixel shr 16 and 0xFF) / 255.0
                val green = (pixel shr 8 and 0xFF) / 255.0
                val blue = (pixel and 0xFF) / 255.0

                // Исключаем цвета, близкие к белому и черному
                if (red > 0.9 && green > 0.9 && blue > 0.9) {
                    continue // Белый цвет
                }
                if (red < 0.4 && green < 0.4 && blue < 0.4) {
                    continue // Черный цвет
                }
                if (red > 0.9 && green > 0.9 && blue > 0.8) {
                    continue // Очень светлый цвет
                }

                colorCount[pixel] = colorCount.getOrDefault(pixel, 0) + 1
            }
        }

        // Находим ключи цветов с количеством больше 50
        val mostCommonPixels = colorCount.filter { it.value > 30 }.keys

        // Сортируем цвета по яркости
        val brightestColors = mostCommonPixels.map { Color(it) }.sortedByDescending { colorBrightness(it) }

        return brightestColors
    }

    fun colorBrightness(color: Color): Double {
        return (color.red * 299 + color.green * 587 + color.blue * 114) / 1000.0
    }

    override fun getAnimeMediaById(id: String): ServiceResponse<AnimeMediaResponse> {
        return try {
            try {
                val criteriaBuilder = entityManager.criteriaBuilder
                val criteriaQuery = criteriaBuilder.createQuery(AnimeMediaTable::class.java)
                val root = criteriaQuery.from(AnimeTable::class.java)

                val joinAnimeTable: Join<AnimeTable, AnimeMediaTable> = root.joinSet("media", JoinType.LEFT)

                criteriaQuery.select(joinAnimeTable)
                    .where(criteriaBuilder.equal(root.get<String>("url"), id))

                val query = entityManager.createQuery(criteriaQuery)

                val mediaList = query.resultList.map { animeMedia ->
                    AnimeMediaResponse.fromAnimeMediaTable(animeMedia)
                }

                ServiceResponse(
                    data = mediaList,
                    message = "Success",
                    status = HttpStatus.OK
                )
            } catch (e: Exception) {
                println(e.message)
                ServiceResponse(
                    data = null,
                    message = "Anime with id = $id not found",
                    status = HttpStatus.NOT_FOUND
                )
            }
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    override fun getAnimeGenres(): ServiceResponse<AnimeGenreTable> {
        return try {
            ServiceResponse(
                data = animeGenreRepository.findAll(),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    override fun getAnimeYears(): ServiceResponse<String> {
        return try {
            ServiceResponse(
                data = animeRepository.findDistinctByYear(),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    override fun getAnimeStudios(): ServiceResponse<AnimeStudiosTable> {
        return try {
            ServiceResponse(
                data = animeStudiosRepository.findAll(),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    override fun getAnimeTranslations(): ServiceResponse<AnimeTranslationTable> {
        return try {
            ServiceResponse(
                data = animeTranslationRepository.findAll(),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: Exception) {
            ServiceResponse(
                data = null,
                message = "Error: ${e.message}",
                status = HttpStatus.BAD_REQUEST
            )
        }
    }

    fun animeLightSuccess(
        animeLight: List<AnimeLight>
    ): ServiceResponse<AnimeLight> {
        return ServiceResponse(
            data = animeLight,
            message = "Success",
            status = HttpStatus.OK
        )
    }

    fun animeToAnimeDetail(
        anime: AnimeTable
    ): AnimeDetail {
        return AnimeDetail(
            url = anime.url,
            title = anime.title,
            image = anime.posterUrl,
            studio = anime.studios.toList(),
            season = anime.season,
            description = anime.description,
            otherTitles = anime.otherTitles.distinct(),
            year = anime.year,
            releasedAt = anime.releasedAt,
            airedAt = anime.airedAt,
            type = anime.type,
            episodesCount = anime.episodesCount,
            episodesCountAired = anime.episodesAires,
            linkPlayer = anime.link,
            genres = anime.genres.toList(),
            status = anime.status,
            ratingMpa = anime.ratingMpa,
            minimalAge = anime.minimalAge
        )
    }

    fun listToAnimeLight(
        anime: List<AnimeTable>
    ): List<AnimeLight> {
        val animeLight = mutableListOf<AnimeLight>()
        anime.forEach {
            animeLight.add(
                AnimeLight(
                    url = it.url,
                    title = it.title,
                    image = it.posterUrl,
                    studio = it.studios.toList(),
                    season = it.season,
                    genres = it.genres.toList(),
                    episodesCount = it.episodesCount,
                    status = it.status,
                    ratingMpa = it.ratingMpa,
                    minimalAge = it.minimalAge,
                    accentColor = it.accentColor,
                    year = it.year,
                    type = it.type
                )
            )
        }
        return animeLight
    }


    override fun addDataToDB(translationID: String) {
        var nextPage: String? = "1"
        val client = HttpClient {
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }
        }

        var ar = runBlocking {
            client.get {
                headers {
                    contentType(ContentType.Application.Json)
                }
                url {
                    protocol = URLProtocol.HTTPS
                    host = "kodikapi.com/list"
                }
                parameter("token", animeToken)
                parameter("limit", 100)
                parameter("sort", "shikimori_rating")
                parameter("order", "desc")
                parameter("types", "anime-serial")
                parameter("camrip", false)
                parameter("with_episodes_data", true)
                parameter("not_blocked_in", "ALL")
                parameter("with_material_data", true)
                parameter(
                    "anime_genres",
                    "безумие, боевые искусства, вампиры, военное, гарем, демоны, детектив, детское, дзёсей, драма, игры, исторический, комедия, космос, машины, меха, музыка, пародия, повседневность, полиция, приключения, психологическое, романтика, самураи, сверхъестественное, спорт, супер сила, сэйнэн, сёдзё, сёдзё-ай, сёнен, сёнен-ай, триллер, ужасы, фантастика, фэнтези, школа, экшен"
                )
                parameter("translation_id", translationID)
            }.body<AnimeResponse>()
        }

        while (nextPage != null) {
            ar.result.forEach Loop@{ anime ->
                println(anime.title)
                val animeTemp = runBlocking {
                    client.get {
                        headers {
                            contentType(ContentType.Application.Json)
                        }
                        url {
                            protocol = URLProtocol.HTTPS
                            host = "kodikapi.com/search"
                        }
                        parameter("token", animeToken)
                        parameter("with_material_data", true)
                        parameter("full_match", true)
                        parameter("title_orig", anime.title)
                    }.body<AnimeTempResponse>()
                }

                anime.materialData = animeTemp.result[0].materialData
                val fr = animeTemp.result[0]
                if (!anime.materialData.title.contains("Атака Титанов") && !anime.materialData.title.contains("Атака титанов")) {
                        val tempingAnime = animeRepository.findByShikimoriId(anime.shikimoriId)
                        if (!tempingAnime.isPresent) {
                            val g = mutableListOf<AnimeGenreTable>()
                            anime.materialData.genres.forEach { genre ->
                                val genreIs = animeGenreRepository.findByGenre(genre).isPresent
                                if (genreIs) {
                                    val temp = animeGenreRepository.findByGenre(genre).get()
                                    g.add(
                                        AnimeGenreTable(id = temp.id, genre = temp.genre)
                                    )
                                } else {
                                    if (genre == "яой" || genre == "эротика" || genre == "хентай" || genre == "Яой" || genre == "Хентай" || genre == "Эротика") {
                                        return@Loop
                                    }
                                    animeGenreRepository.save(
                                        AnimeGenreTable(genre = genre)
                                    )
                                    g.add(
                                        animeGenreRepository.findByGenre(genre = genre).get()
                                    )
                                }
                            }
                            val st = mutableListOf<AnimeStudiosTable>()
                            anime.materialData.animeStudios.forEach { studio ->
                                val studioIs = animeStudiosRepository.findByStudio(studio).isPresent
                                if (studioIs) {
                                    val temp = animeStudiosRepository.findByStudio(studio).get()
                                    st.add(
                                        AnimeStudiosTable(id = temp.id, studio = temp.studio)
                                    )
                                } else {
                                    animeStudiosRepository.save(
                                        AnimeStudiosTable(studio = studio)
                                    )
                                    st.add(
                                        animeStudiosRepository.findByStudio(studio = studio).get()
                                    )
                                }
                            }
                            val translationIs = animeTranslationRepository.findById(anime.translation.id).isPresent
                            val t = if (translationIs) {
                                val temp = animeTranslationRepository.findById(anime.translation.id).get()
                                AnimeTranslationTable(id = temp.id, title = temp.title, voice = temp.voice)
                            } else {
                                animeTranslationRepository.save(
                                    AnimeTranslationTable(
                                        id = anime.translation.id,
                                        title = anime.translation.title,
                                        voice = anime.translation.voice
                                    )
                                )
                                animeTranslationRepository.findById(anime.translation.id).get()
                            }

                            Thread.sleep(1000)
                            val mediaTemp = runBlocking {
                                client.get {
                                    headers {
                                        contentType(ContentType.Application.Json)
                                    }
                                    url {
                                        protocol = URLProtocol.HTTPS
                                        host = "shikimori.me/api/animes/${anime.shikimoriId}"
                                    }
                                }.body<AnimeMediaParse>()
                            }

                            val media = mediaTemp.videos.map { video ->
                                if (video.hosting != "vk") {
                                    animeMediaRepository.save(
                                        AnimeMediaTable(
                                            url = video.url,
                                            imageUrl = video.imageUrl,
                                            playerUrl = video.playerUrl,
                                            name = video.name,
                                            kind = video.kind,
                                            hosting = video.hosting
                                        )
                                    )
                                } else {
                                    null
                                }
                            }

                            val tempingAnimeImage = animeRepository.findByPosterUrl(fr.materialData.poster).isPresent

                            var image: BufferedImage? = null

                            try {
                                val url = if(tempingAnimeImage) {
                                    "https://shikimori.me/system/animes/original/${anime.shikimoriId}.jpg"
                                } else {
                                    val regex = Regex("\\b[0-9]\\b")
                                    if (regex.containsMatchIn(anime.materialData.title)) {
                                        "https://shikimori.me/system/animes/original/${anime.shikimoriId}.jpg"
                                    } else {
                                        fr.materialData.poster
                                    }
                                }
                                val urlObj = URL(if (url.startsWith("https://")) {
                                    url
                                } else {
                                    "https://$url"
                                })
                                image = ImageIO.read(urlObj)
                            } catch (e: IOException) {
                                e.printStackTrace()
                                return@Loop
                            }

                            val a = AnimeTable(
                                title = anime.materialData.title,
                                titleEn = anime.title,
                                url = if(anime.materialData.title.isNotEmpty()) translit(anime.materialData.title) else if(anime.title.isNotEmpty()) translit(anime.title) else "",
                                otherTitles = anime.materialData.otherTitles.toMutableList(),
                                status = anime.materialData.animeStatus,
                                description = anime.materialData.description,
                                year = anime.materialData.year,
                                createdAt = anime.createdAt,
                                link = anime.link,
                                airedAt = anime.materialData.airedAt,
                                releasedAt = anime.materialData.releasedAt,
                                episodesCount = anime.materialData.episodesTotal,
                                episodesAires = anime.materialData.episodesAired,
                                posterUrl = if(tempingAnimeImage || Regex("[1-9]").containsMatchIn(anime.materialData.title)) {
                                    "https://shikimori.me/system/animes/original/${anime.shikimoriId}.jpg"
                                } else fr.materialData.poster,
                                type = anime.materialData.animeType,
                                updatedAt = anime.updatedAt,
                                minimalAge = anime.materialData.minimalAge,
                                screenshots = anime.screenshots.toMutableList(),
                                ratingMpa = when (anime.materialData.ratingMpa) {
                                    "PG" -> "PG"
                                    "Rx" -> "R+"
                                    "R+" -> "R+"
                                    "PG-13" -> "PG-13"
                                    "pg" -> "PG"
                                    "R" -> "R"
                                    "pg13" -> "PG-13"
                                    "G" -> "G"
                                    else -> ""
                                },
                                shikimoriId = anime.shikimoriId,
                                shikimoriRating = anime.materialData.shikimoriRating,
                                shikimoriVotes = anime.materialData.shikimoriVotes,
                                season = when (anime.materialData.airedAt.month.value) {
                                    12, 1, 2 -> "Winter"
                                    3, 4, 5 -> "Spring"
                                    6, 7, 8 -> "Summer"
                                    else -> "Fall"
                                },
                                accentColor = getMostCommonColor(image!!)
                            )
                            a.addAllAnimeGenre(g)
                            a.addAllAnimeStudios(st)
                            a.addTranslation(t)
                            a.addMediaAll(media.filterNotNull())
                            animeRepository.save(a)
                        } else {
                            val a = animeRepository.findByShikimoriIdWithTranslation(anime.shikimoriId).get()

                            val translationIs = animeTranslationRepository.findById(anime.translation.id).isPresent
                            val t = if (translationIs) {
                                val temp = animeTranslationRepository.findById(anime.translation.id).get()
                                AnimeTranslationTable(id = temp.id, title = temp.title, voice = temp.voice)
                            } else {
                                animeTranslationRepository.save(
                                    AnimeTranslationTable(
                                        id = anime.translation.id,
                                        title = anime.translation.title,
                                        voice = anime.translation.voice
                                    )
                                )
                                animeTranslationRepository.findById(anime.translation.id).get()
                            }
                            val existingTranslation = a.translation.find { it.title == t.title }
                            if (existingTranslation == null) {
                                a.addTranslation(t)
                            }
//                            if (a.status != anime.materialData.animeStatus) {
//                                a.status = anime.materialData.animeStatus
//                                animeRepository.save(a)
//                            }
                        }
                }
            }
            if (ar.nextPage != null) {
                ar = runBlocking {
                    client.get(ar.nextPage!!) {
                        headers {
                            contentType(ContentType.Application.Json)
                        }
                    }.body()
                }
            }
            nextPage = ar.nextPage
            Thread.sleep(5000)
        }
    }
}
