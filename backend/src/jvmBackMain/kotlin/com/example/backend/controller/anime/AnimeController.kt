package com.example.backend.controller.anime

import com.example.backend.models.ServiceResponse
import com.example.backend.service.anime.AnimeService
import com.example.common.models.animeResponse.light.AnimeLight
import io.swagger.v3.oas.annotations.Operation
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

    @GetMapping()
    @Operation(summary = "get all anime")
    fun getAnime(
        @RequestParam(defaultValue = "0", name = "pageNum")  pageNum: @Min(0) @Max(500) Int,
        @RequestParam(defaultValue = "48", name = "pageSize") pageSize: @Min(1) @Max(500) Int,
        @RequestParam(required = false) genres: List<String>?,
        @Schema(name = "status", required = false, description = "Must be one of: онгоинг, завершён", nullable = true) status: String?,
        @Schema(name = "order", required = false, description = "Must be one of: random, popular, views", nullable = true) order: String?,
        @Schema(name = "searchQuery", required = false, nullable = true) searchQuery: String?
    ): ServiceResponse<AnimeLight>? {
        return try {
            animeService.getAnime(
                pageNum = pageNum,
                pageSize = pageSize,
                genres = genres,
                status = status,
                order = order,
                searchQuery = searchQuery
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }
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