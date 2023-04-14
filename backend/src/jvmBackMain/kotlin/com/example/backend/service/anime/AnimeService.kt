package com.example.backend.service.anime

//import com.example.backend.elasticRepository.AnimeElasticRepository
import com.example.backend.jpa.anime.*
import com.example.backend.models.ServiceResponse
import com.example.backend.models.animeParser.AnimeResponse
import com.example.backend.models.animeParser.AnimeTempResponse
import com.example.backend.models.animeResponse.detail.AnimeDetail
import com.example.backend.models.animeResponse.light.AnimeLight
import com.example.backend.repository.anime.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
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
import java.util.*
import kotlin.collections.ArrayList


@Service
class AnimeService: AnimeRepositoryImpl {

    @Value("\${anime.ko.token}")
    lateinit var animeToken: String

    @Autowired
    lateinit var animeSeasonRepository: AnimeSeasonRepository

    @Autowired
    lateinit var animeEpisodeRepository: AnimeEpisodeRepository

    @Autowired
    lateinit var animeStudiosRepository: AnimeStudiosRepository

    @Autowired
    lateinit var animeGenreRepository: AnimeGenreRepository

    @Autowired
    lateinit var animeTranslationRepository: AnimeTranslationRepository

    @Autowired
    lateinit var animeRepository: AnimeRepository

//    @Autowired
//    lateinit var animeElasticRepository: AnimeElasticRepository


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
        println("PAGEABLE = ${pageable.pageSize} | ${pageable.pageNumber}")
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

    @PersistenceContext
    private lateinit var entityManager: EntityManager

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
            predicates.add(criteriaBuilder.equal(root.get<String>("title"), searchQuery))
        }
        if (!translationIds.isNullOrEmpty()) {
            val episodesJoin = root.join<AnimeTable, AnimeSeasonTable>("seasons").join<AnimeSeasonTable, AnimeEpisodeTable>("episodes")
            val translationsJoin = episodesJoin.join<AnimeEpisodeTable, AnimeTranslationTable>("translation")
            val translationIdsPredicate = criteriaBuilder.isTrue(
                translationsJoin.get<AnimeTranslationTable>("id").`in`(translationIds)
            )
            predicates.add(translationIdsPredicate)
        }

        if (predicates.isNotEmpty()) {
            criteriaQuery.where(*predicates.toTypedArray())
        }

        val query = entityManager.createQuery(criteriaQuery)
        query.firstResult = pageable.pageNumber * pageable.pageSize
        query.maxResults = pageable.pageSize + 1

        return query.resultList
    }

    override fun getAnimeById(id: String): ServiceResponse<AnimeDetail> {
        return try {
            try {
                val anime = animeRepository.findById(id).get()
                ServiceResponse(
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
            id = anime.id,
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
                    id = it.id,
                    title = it.title,
                    image = it.posterUrl,
                    studio = it.studios.toList(),
                    season = it.season,
                    genres = it.genres.toList(),
                    episodesCount = it.episodesCount,
                    status = it.status,
                    ratingMpa = it.ratingMpa,
                    minimalAge = it.minimalAge
                )
            )
        }
        return animeLight
    }


    override fun addDataToDB() {
        var nextPage: String? = "1"
        val client = HttpClient {
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
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
                parameter("translation_id", "609, 610, 735, 643, 559, 739, 767, 825, 1895, 704, 923, 933, 557, 794")
            }.body<AnimeResponse>()
        }

        while (nextPage != null) {
            ar.result.forEach Loop@{ anime ->
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
                    println("WAFL = ${anime.materialData.title}")
                        val tempingAnime = animeRepository.findByTitle(anime.materialData.title)
                        println(tempingAnime.isPresent)
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
                            val se = mutableListOf<AnimeSeasonTable>()
                            anime.seasons.forEach { season ->
                                val episodes = ArrayList<AnimeEpisodeTable>()
                                season.value.episodes.values.forEach waf@{ ep ->
                                    if (animeEpisodeRepository.findByEpisodeLink(ep.link).isPresent) {
                                        return@waf
                                    } else {
                                        val temp = animeEpisodeRepository.save(
                                            AnimeEpisodeTable(
                                                link = ep.link,
                                                screenshots = ep.screenshots.toMutableList()
                                            )
                                        )
                                        temp.addTranslationToEpisode(listOf(t))
                                        episodes.add(temp)
                                    }
                                }
                                val tempSeason = animeSeasonRepository.save(
                                    AnimeSeasonTable(
                                        link = season.value.link,
                                        season = season.key
                                    )
                                )
                                tempSeason.addEpisodes(episodes)
                                se.add(tempSeason)
                            }

                            val a = AnimeTable(
                                title = anime.materialData.title,
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
                                posterUrl = fr.materialData.poster,
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
                                }
                            )
                            a.addAllAnimeGenre(g)
                            a.addAllAnimeStudios(st)
                            a.addSeason(se)
                            animeRepository.save(a)
                        } else {
                            val a = animeRepository.findByTitle(anime.materialData.title).get()
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
                            anime.seasons.forEach { season ->
                                val episodes = ArrayList<AnimeEpisodeTable>()
                                season.value.episodes.values.forEach season@{ ep ->
                                    if (animeEpisodeRepository.findByEpisodeLink(ep.link).isPresent) {
                                        return@season
                                    } else {
                                        val temp = animeEpisodeRepository.save(
                                            AnimeEpisodeTable(
                                                link = ep.link,
                                                screenshots = ep.screenshots.toMutableList()
                                            )
                                        )
                                        temp!!.addTranslationToEpisode(
                                            translations = listOf(t)
                                        )
                                        episodes.add(temp)
                                    }
                                }
                                if (episodes.size > 0) {
                                    a.seasons.forEach {
                                        it.addEpisodes(episodes)
                                    }
                                }
                            }
                            if (a.status != anime.materialData.animeStatus) {
                                a.status = anime.materialData.animeStatus
                                animeRepository.save(a)
                            }
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
