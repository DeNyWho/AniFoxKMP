package com.example.common.data.repository

import com.example.common.core.error.GeneralError
import com.example.common.core.safeApiCall
import com.example.common.core.wrapper.Resource
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse
import com.example.common.network.MangaApi
import com.example.common.util.Endpoints
import com.example.common.util.Endpoints.BASE_URL
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

class MangaRepository(private val client: HttpClient): KoinComponent, MangaApi {
    override suspend fun getManga(
        pageNum: Int,
        pageSize: Int,
        order: String?,
        status: String?,
        genres: List<String>?,
        searchQuery: String?
    ): Resource<ServiceResponse<MangaLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = BASE_URL
                encodedPath = Endpoints.manga
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
                if(order != null) parameter("order", order)
                if(status != null) if(status.length > 4) parameter("status", status)
                if(genres!= null) if(genres.isNotEmpty()) parameter("genres", genres)
                if(searchQuery!= null) if(searchQuery.length > 1) parameter("searchQuery", searchQuery)
            }
        }

        return safeApiCall<ServiceResponse<MangaLight>, GeneralError>(client, request)
    }

    override suspend fun getMangaDetails(
        id: String
    ): Resource<ServiceResponse<MangaDetail>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.manga}$id"
            }
        }

        return safeApiCall<ServiceResponse<MangaDetail>, GeneralError>(client, request)
    }

    override suspend fun getMangaLinked(
        id: String
    ): Resource<ServiceResponse<MangaLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.manga}$id${Endpoints.linked}"
            }
        }

        return safeApiCall<ServiceResponse<MangaLight>, GeneralError>(client, request)
    }

    override suspend fun getMangaSimilar(
        id: String,
        pageNum: Int,
        pageSize: Int,
    ): Resource<ServiceResponse<MangaLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.manga}$id${Endpoints.similar}"
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
            }
        }

        return safeApiCall<ServiceResponse<MangaLight>, GeneralError>(client, request)
    }
}