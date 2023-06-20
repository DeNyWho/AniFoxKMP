package com.example.common.data.repository

import com.example.common.core.error.GeneralError
import com.example.common.core.safeApiCall
import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.network.UserApi
import com.example.common.util.Endpoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent


class UserRepository(private val client: HttpClient): KoinComponent, UserApi {
    override suspend fun getAnimeFavoriteList(
        pageNum: Int,
        pageSize: Int,
        token: String,
        status: String,
    ): Resource<List<AnimeLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.users}${Endpoints.animeFav}/$status"
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
            }
            header("Authorization", "Bearer $token")
        }

        return safeApiCall<List<AnimeLight>, GeneralError>(client, request)
    }

    override suspend fun getMangaFavoriteList(
        pageNum: Int,
        pageSize: Int,
        token: String,
        status: String,
    ): Resource<List<MangaLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.users}${Endpoints.mangaFav}/$status"
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
            }
            header("Authorization", "Bearer $token")
        }

        return safeApiCall<List<MangaLight>, GeneralError>(client, request)
    }

    override suspend fun setMangaFavoriteList(
        token: String,
        id: String,
        status: String,
    ): Resource<String> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.users}${Endpoints.mangaFav}"
                parameter("id", id)
                parameter("status", status)
            }
            header("Authorization", "Bearer $token")
        }

        return safeApiCall<String, GeneralError>(client, request)
    }

    override suspend fun setAnimeFavoriteList(
        token: String,
        url: String,
        status: String,
    ): Resource<String> {
        println("ZZ X C")
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.users}${Endpoints.animeFav}"
                parameter("url", url)
                parameter("status", status)
            }
            header("Authorization", "Bearer $token")
        }

        return safeApiCall<String, GeneralError>(client, request)
    }



}