package com.example.common.data.repository

import com.example.common.core.error.GeneralError
import com.example.common.core.safeApiCall
import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.detail.AnimeDetail
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.response.ServiceResponse
import com.example.common.network.AnimeApi
import com.example.common.util.Endpoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent


class AnimeRepository(private val client: HttpClient): KoinComponent, AnimeApi {
    override suspend fun getAnime(
        pageNum: Int,
        pageSize: Int,
        order: String?,
        status: String?,
        genres: List<String>?,
        searchQuery: String?,
        season: String?,
        ratingMpa: String?,
        minimalAge: String?,
        type: String?
    ): Resource<ServiceResponse<AnimeLight>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = Endpoints.BASE_URL
                encodedPath = Endpoints.anime
                parameter("pageNum", pageNum)
                parameter("pageSize", pageSize)
                if(order != null) parameter("order", order)
                if(season != null) parameter("season", season)
                if(ratingMpa != null) parameter("ratingMpa", ratingMpa)
                if(minimalAge != null) parameter("minimalAge", minimalAge)
                if(type != null) parameter("type", type)
                if(status != null) if(status.length > 4) parameter("status", status)
                if(genres!= null) if(genres.isNotEmpty()) parameter("genres", genres)
                if(searchQuery!= null) if(searchQuery.length > 1) parameter("searchQuery", searchQuery)
            }
        }

        return safeApiCall<ServiceResponse<AnimeLight>, GeneralError>(client, request)
    }

    override suspend fun getAnimeDetails(
        id: String
    ): Resource<ServiceResponse<AnimeDetail>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTP
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.anime}$id"
            }
        }

        return safeApiCall<ServiceResponse<AnimeDetail>, GeneralError>(client, request)
    }
}