package com.example.common.repository

import com.example.common.core.error.GeneralError
import com.example.common.core.safeApiCall
import com.example.common.core.wrapper.Resource
import com.example.common.models.GenreRequest
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.models.response.ServiceResponse
import com.example.common.network.MangaApi
import com.example.common.util.Endpoints
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
        genres: List<String>?
    ): Resource<ServiceResponse<MangaLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = Endpoints.BASE_URL
                encodedPath = Endpoints.manga
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
                parameter("order", order)
                parameter("status", status)
                parameter("genres", genres)
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