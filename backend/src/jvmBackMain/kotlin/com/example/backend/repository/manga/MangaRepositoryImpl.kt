package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse

interface MangaRepositoryImpl {

    fun addDataToDB(): MangaTable
    fun getAllManga(): List<MangaLight>
    fun getMangaById(id: String): ServiceResponse<MangaDetail>
}