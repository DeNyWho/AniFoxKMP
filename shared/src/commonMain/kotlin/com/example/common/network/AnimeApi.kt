package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.detail.AnimeDetail
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.response.ServiceResponse

interface AnimeApi {
    suspend fun getAnime(
        pageNum: Int,
        pageSize: Int,
        order: String?,
        status: String?,
        genres: List<String>?,
        searchQuery: String?,
        season: String?,
        ratingMpa: String?,
        minimalAge: String?,
        type: String?,
        year: Int?
    ): Resource<ServiceResponse<AnimeLight>>

    suspend fun getAnimeDetails(id: String): Resource<ServiceResponse<AnimeDetail>>
}