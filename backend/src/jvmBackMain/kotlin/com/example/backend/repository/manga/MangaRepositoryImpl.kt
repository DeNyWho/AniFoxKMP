package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable
import com.example.common.models.mangaResponse.chapters.ChaptersLight
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.backend.models.ServiceResponse
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

interface MangaRepositoryImpl {

    fun addDataToDB(): MangaTable
    fun getAllManga(
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int,
        order: String?,
        genre: List<String>?,
        status: String?
    ): ServiceResponse<MangaLight>
    fun getMangaById(id: String): ServiceResponse<MangaDetail>
    fun getMangaChapters(
        id: String,
        pageNum: @Min(value = 0.toLong()) @Max(value = 500.toLong()) Int,
        pageSize: @Min(value = 1.toLong()) @Max(value = 500.toLong()) Int
    ): ServiceResponse<ChaptersLight>

    fun getMangaLinked(id: String): ServiceResponse<MangaLight>
    fun findManga(searchQuery: String, pageNum: Int, pageSize: Int): ServiceResponse<MangaLight>
}