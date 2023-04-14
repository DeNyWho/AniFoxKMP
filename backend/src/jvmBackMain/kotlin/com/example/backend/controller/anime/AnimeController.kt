package com.example.backend.controller.anime

import com.example.backend.jpa.anime.AnimeGenreTable
import com.example.backend.jpa.anime.AnimeStudiosTable
import com.example.backend.jpa.anime.AnimeTranslationTable
import com.example.backend.models.ServiceResponse
import com.example.backend.models.animeResponse.detail.AnimeDetail
import com.example.backend.models.animeResponse.light.AnimeLight
import com.example.backend.service.anime.AnimeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*



@RestController
@CrossOrigin("*")
@Tag(name = "AnimeApi", description = "All about anime")
@RequestMapping("/api/anime/")
class AnimeController {

    @Autowired
    lateinit var animeService: AnimeService

    @GetMapping
    @Operation(summary = "get all anime")
    fun getAnime(
        @RequestParam(defaultValue = "0", name = "pageNum")  pageNum: @Min(0) @Max(500) Int,
        @RequestParam(defaultValue = "48", name = "pageSize") pageSize: @Min(1) @Max(500) Int,
        @RequestParam(name = "genres", required = false)
        @Parameter(name = "genres", description = "Require genres IDS", required = false)
        genres: List<String>?,
        @Schema(name = "status", required = false, description = "Must be one of: released | ongoing", nullable = true) status: String?,
        @Schema(name = "order", required = false, description = "Must be one of: random | popular | views", nullable = true) order: String?,
        @Schema(name = "searchQuery", required = false, nullable = true) searchQuery: String?,
        @Schema(name = "season", required = false, nullable = true, description = "Must be one of: Winter | Spring | Summer | Fall") season: String?,
        @Schema(name = "ratingMpa", required = false, nullable = true, description = "Must be one of: PG | PG-13 | R | R+ | G") ratingMpa: String?,
        @Schema(name = "minimalAge", required = false, nullable = true, description = "Must be one of: 18 | 16 | 12 | 6 | 0") minimalAge: Int?,
        @Schema(name = "type", required = false, nullable = true, description = "Must be one of: movie | ona | ova | music | special | tv") type: String?,
        @RequestParam(name = "year", required = false)
        @Parameter(name = "year", description = "Require list of year", required = false)
        year: List<Int>?,
        @RequestParam(name = "translation", required = false)
        @Parameter(name = "translation", description = "Require translation IDS", required = false)
        translations: List<String>?
    ): ServiceResponse<AnimeLight>? {
        return try {
            animeService.getAnime(
                pageNum = pageNum,
                pageSize = pageSize,
                genres = genres,
                status = status,
                order = order,
                searchQuery = searchQuery,
                season = season,
                ratingMpa = ratingMpa,
                minimalAge = minimalAge,
                type = type,
                year = year,
                translations = translations
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "detail anime query")
    fun getAnimeDetails(
        @PathVariable id: String
    ): ServiceResponse<AnimeDetail>? {
        return try {
            animeService.getAnimeById(id)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("genres")
    @Operation(summary = "get all anime genres")
    fun getAnimeGenres(): ServiceResponse<AnimeGenreTable>? {
        return try {
            animeService.getAnimeGenres()
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("years")
    @Operation(summary = "get all anime years")
    fun getAnimeYears(): ServiceResponse<String>? {
        return try {
            animeService.getAnimeYears()
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("studios")
    @Operation(summary = "get all anime studios")
    fun getAnimeStudios(): ServiceResponse<AnimeStudiosTable>? {
        return try {
            animeService.getAnimeStudios()
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("translations")
    @Operation(summary = "get all anime translations")
    fun getAnimeTranslations(): ServiceResponse<AnimeTranslationTable>? {
        return try {
            animeService.getAnimeTranslations()
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }
//
//    @GetMapping("parser")
//    @Operation(summary = "Parse manga and add data to postgreSQL")
//    fun parseAnime(): ServiceResponse<Long> {
//        return try {
//            val start = System.currentTimeMillis()
//            animeService.addDataToDB()
//
//            val finish = System.currentTimeMillis()
//            val elapsed = finish - start
//            println("Time execution $elapsed")
//            return ServiceResponse(data = listOf(elapsed), status = HttpStatus.OK)
//        } catch (e: ChangeSetPersister.NotFoundException) {
//            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
//        }
//    }

}