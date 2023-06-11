package com.example.common.data.repository

import com.example.common.core.enum.StatusListType
import com.example.common.core.error.GeneralError
import com.example.common.core.safeApiCall
import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.response.ServiceResponse
import com.example.common.network.AnimeApi
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
        status: StatusListType,
    ): Resource<ServiceResponse<AnimeLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = Endpoints.anime
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
                parameter("status", status.name)
            }
            header("Authorization", "Bearer $token")
        }

        return safeApiCall<ServiceResponse<AnimeLight>, GeneralError>(client, request)
    }

}