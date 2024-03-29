package com.example.backend.controller.manga

import com.example.backend.models.ServiceResponse
import com.example.backend.models.mangaResponse.chapters.ChapterSingle
import com.example.backend.models.mangaResponse.chapters.ChaptersLight
import com.example.backend.models.mangaResponse.detail.MangaDetail
import com.example.backend.models.mangaResponse.light.MangaLight
import com.example.backend.service.manga.MangaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@RestController
@CrossOrigin("*")
@Tag(name = "MangaApi", description = "All about manga")
@RequestMapping("/api/manga/")
class MangaController {

    @Autowired
    lateinit var mangaService: MangaService


//    @GetMapping("parser")
//    @Operation(summary = "Parse manga and add data to postgreSQL")
//    fun parseManga(): ServiceResponse<Long> {
//        return try {
//            val start = System.currentTimeMillis()
//            mangaService.addDataToDB()
//
//            val finish = System.currentTimeMillis()
//            val elapsed = finish - start
//            println("Time execution $elapsed")
//            return ServiceResponse(data = listOf(elapsed), status = HttpStatus.OK)
//        } catch (e: ChangeSetPersister.NotFoundException) {
//            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
//        }
//    }

    @GetMapping()
    @Operation(summary = "get all manga")
    fun getManga(
        @RequestParam(defaultValue = "0", name = "pageNum")  pageNum: @Min(0) @Max(500) Int,
        @RequestParam(defaultValue = "48", name = "pageSize") pageSize: @Min(1) @Max(500) Int,
        @RequestParam(required = false) genres: List<String>?,
        @Schema(name = "status", required = false, description = "Must be one of: онгоинг, завершён, продолжается, заброшен", nullable = true) status: String?,
        @Schema(name = "order", required = false, description = "Must be one of: random, popular, views", nullable = true) order: String?,
        @Schema(name = "searchQuery", required = false, nullable = true) searchQuery: String?
    ): ServiceResponse<MangaLight>? {
        return try {
            mangaService.getAllManga(
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

    @GetMapping("{id}/chapters")
    @Operation(summary = "get manga chapters")
    fun getMangaChapters(
        @RequestParam(defaultValue = "0", name = "pageNum")  pageNum: @Min(0) @Max(500) Int,
        @RequestParam(defaultValue = "48", name = "pageSize") pageSize: @Min(1) @Max(500) Int,
        response: HttpServletResponse, @PathVariable(name = "id") id: String
    ): ServiceResponse<ChaptersLight> {
        return try {
            mangaService.getMangaChapters(id, pageNum, pageSize)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("{mangaId}/chapters/{chapterId}")
    @Operation(summary = "get manga chapters")
    fun getMangaChaptersPages(
        response: HttpServletResponse,
        @PathVariable(name = "mangaId") mangaId: String,
        @PathVariable(name = "chapterId") chapterId: String
    ): ServiceResponse<ChapterSingle> {
        return try {
            mangaService.getMangaChapter(chapterId = chapterId, mangaId = mangaId)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("{id}/linked")
    @Operation(summary = "get manga linked")
    fun getMangaLinked(
        response: HttpServletResponse, @PathVariable(name = "id") id: String
    ): ServiceResponse<MangaLight> {
        return try {
            mangaService.getMangaLinked(id)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("{id}/similar")
    @Operation(summary = "get manga similar")
    fun getMangaSimilar(
        @PathVariable(name = "id") id: String,
        @RequestParam(defaultValue = "0", name = "pageNum")  pageNum: @Min(0) @Max(500) Int,
        @RequestParam(defaultValue = "48", name = "pageSize") pageSize: @Min(1) @Max(500) Int,
        response: HttpServletResponse
    ): ServiceResponse<MangaLight> {
        return try {
            mangaService.getSimilarManga(pageNum, pageSize, id)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "get manga by id")
    fun getMangaById(@PathVariable(name = "id") id: String): ServiceResponse<MangaDetail> {
        return try {
            mangaService.getMangaById(id)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }
}