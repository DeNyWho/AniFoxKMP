package com.example.backend.controller.manga

import com.example.backend.service.manga.MangaService
import com.example.common.models.mangaResponse.chapters.ChaptersLight
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@Tag(name = "MangaApi", description = "All about manga")
@RequestMapping("/api/manga/")
class MangaController {

    @Autowired
    lateinit var mangaService: MangaService


    @GetMapping("parser")
    @Operation(summary = "Parse manga and add data to postgreSQL")
    fun parseManga(): ServiceResponse<Long> {
        return try {
            val start = System.currentTimeMillis()
            mangaService.addDataToDB()

            val finish = System.currentTimeMillis()
            val elapsed = finish - start
            println("Time execution $elapsed")
            return ServiceResponse(data = listOf(elapsed), status = HttpStatus.OK)
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping()
    @Operation(summary = "get all manga")
    fun getManga(): ServiceResponse<MangaLight> {
        return try {
            return ServiceResponse(data = mangaService.getAllManga(), status = HttpStatus.OK)
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

    @GetMapping("search")
    @Operation(summary = "manga search")
    fun searchManga(
        @RequestParam(name = "searchQuery") searchQuery: String,
        @RequestParam(defaultValue = "0", name = "pageNum")  pageNum: @Min(0) @Max(500) Int,
        @RequestParam(defaultValue = "48", name = "pageSize") pageSize: @Min(1) @Max(500) Int,
        response: HttpServletResponse
    ): ServiceResponse<MangaLight> {
        return try {
            mangaService.findManga(searchQuery, pageNum, pageSize)
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