package com.example.backend.repository.anime

import com.example.backend.models.ServiceResponse
import com.example.common.models.animeResponse.light.AnimeLight
import org.springframework.stereotype.Repository

@Repository
interface AnimeRepositoryImpl {

    fun addDataToDB()
    fun getAnime(
        pageNum: Int,
        pageSize: Int,
        order: String?,
        genres: List<String>?,
        status: String?,
        searchQuery: String?
    ): ServiceResponse<AnimeLight>
}