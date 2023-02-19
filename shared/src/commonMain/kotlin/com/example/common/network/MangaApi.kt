package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.GenreRequest
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse
import kotlinx.coroutines.CoroutineDispatcher

expect val ApplicationDispatcher: CoroutineDispatcher

interface MangaApi {
    suspend fun getManga(pageNum: Int, pageSize: Int, order: String?, status: String?, genres: List<String>?): Resource<ServiceResponse<MangaLight>>
}
