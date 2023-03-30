package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable
import com.example.backend.models.ServiceResponse
import com.example.backend.models.mangaResponse.chapters.ChapterSingle
import com.example.backend.models.mangaResponse.chapters.ChaptersLight
import com.example.backend.models.mangaResponse.detail.MangaDetail
import com.example.backend.models.mangaResponse.light.MangaLight
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

interface MangaRepositoryImpl {

    fun addDataToDB(): MangaTable
    fun getMangaById(id: String): ServiceResponse<MangaDetail>
    fun getMangaChapters(
        id: String,
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int
    ): ServiceResponse<ChaptersLight>

    fun getMangaLinked(id: String): ServiceResponse<MangaLight>

    fun getSimilarManga(pageNum: Int, pageSize: Int, id: String): ServiceResponse<MangaLight>

    fun getAllManga(
        pageNum: Int,
        pageSize: Int,
        order: String?,
        genres: List<String>?,
        status: String?,
        searchQuery: String?
    ): ServiceResponse<MangaLight>

    fun getMangaChapter(mangaId: String, chapterId: String): ServiceResponse<ChapterSingle>
}