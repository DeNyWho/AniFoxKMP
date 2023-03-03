package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.GenreRequest
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse
import kotlinx.coroutines.CoroutineDispatcher

expect val ApplicationDispatcher: CoroutineDispatcher

interface MangaApi {
    suspend fun getManga(pageNum: Int, pageSize: Int, order: String?, status: String?, genres: List<String>?, searchQuery: String?): Resource<ServiceResponse<MangaLight>>
    suspend fun getMangaDetails(id: String): Resource<ServiceResponse<MangaDetail>>
    suspend fun getMangaLinked(id: String): Resource<ServiceResponse<MangaLight>>
    suspend fun getMangaSimilar(id: String, pageNum: Int, pageSize: Int): Resource<ServiceResponse<MangaLight>>
}
