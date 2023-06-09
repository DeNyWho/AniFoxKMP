package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.AnimeRelatedLight
import com.example.common.models.animeResponse.detail.AnimeDetail
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.common.ContentMedia
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
    suspend fun getAnimeScreenshots(url: String): Resource<ServiceResponse<String>>
    suspend fun getAnimeMedia(url: String): Resource<ServiceResponse<ContentMedia>>
    suspend fun getAnimeSimilar(url: String): Resource<ServiceResponse<AnimeLight>>
    suspend fun getAnimeRelated(url: String): Resource<ServiceResponse<AnimeRelatedLight>>
}