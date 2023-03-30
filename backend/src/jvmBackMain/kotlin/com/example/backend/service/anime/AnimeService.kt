package com.example.backend.service.anime

import com.example.backend.jpa.anime.*
import com.example.backend.models.ServiceResponse
import com.example.backend.models.animeParser.AnimeResponse
import com.example.backend.models.animeParser.AnimeTempResponse
import com.example.common.models.animeResponse.detail.AnimeDetail
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.backend.repository.anime.*
import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
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
import java.time.LocalDate


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


    override fun getAnime(
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int,
        order: String?,
        genres: List<String>?,
        status: String?,
        searchQuery: String?,
        ratingMpa: String?,
        season: String?,
        minimalAge: Int?
    ): ServiceResponse<AnimeLight> {
        val actualStatus = status?.ifEmpty { null }
        val actualSearch = searchQuery?.ifEmpty { null }
        println("Anime param = $pageNum | $pageSize | $order | ${genres?.size} | $status")
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
                animeRepository.findAnime(
                    pageable = pageable,
                    status = actualStatus,
                    searchQuery = actualSearch,
                    ratingMpa = ratingMpa,
                    season = season,
                    minimalAge = minimalAge
                )
            )
        )
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
            studio = anime.studios.map { studio ->
                AnimeStudios(id = studio.id, studio = studio.studio)
            },
            season = anime.season,
            description = anime.description,
            otherTitles = anime.otherTitles,
            year = anime.year,
            releasedAt = anime.releasedAt,
            airedAt = anime.airedAt,
            type = anime.type,
            episodesCount = anime.episodesCount,
            episodesCountAired = anime.episodesAires,
            genres = anime.genres.map { genre ->
                AnimeGenres(id = genre.id, genre = genre.genre)
            },
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
                    studio = it.studios.map { studio ->
                        AnimeStudios(id = studio.id, studio = studio.studio)
                    },
                    season = it.season,
                    genres = it.genres.map { genre ->
                        AnimeGenres(id = genre.id, genre = genre.genre)
                    },
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

                        }
                        parameter("token", animeToken)
                        parameter("with_material_data", true)
                        parameter("full_match", true)
                        parameter("title_orig", anime.title)
                    }.body<AnimeTempResponse>()
                }
                anime.materialData = animeTemp.result[0].materialData
                val fr = animeTemp.result[0]
                if (anime.materialData.episodesAired == 0) return@Loop
                if (!anime.materialData.title.contains("Атака Титанов") && !anime.materialData.title.contains("Атака титанов")) {
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
                                if (genre == "яой" || genre == "эротика" || genre == "хентай") {
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
                            season.value.episodes.values.forEach { ep ->
                                val temp = animeEpisodeRepository.save(
                                    AnimeEpisodeTable(
                                        link = ep.link,
                                        screenshots = ep.screenshots.toMutableList()
                                    )
                                )
                                temp.addTranslationToEpisode(listOf(t))
                                episodes.add(temp)
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
                                            screenshots = ep.screenshots.toMutableList(),
                                        )
                                    )
                                    temp.addTranslationToEpisode(
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
