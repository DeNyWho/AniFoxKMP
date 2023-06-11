package com.example.common.data.repository

import com.example.common.core.error.GeneralError
import com.example.common.core.safeApiCall
import com.example.common.core.wrapper.Resource
import com.example.common.models.auth.LoginRequest
import com.example.common.models.auth.SignUpRequest
import com.example.common.models.auth.TokenResponse
import com.example.common.network.AuthApi
import com.example.common.util.Endpoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class AuthRepository(private val client: HttpClient): KoinComponent, AuthApi {
    @OptIn(InternalAPI::class)
    override suspend fun login(
        email: String,
        password: String
    ): Resource<TokenResponse> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.auth}${Endpoints.login}"
            }
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(LoginRequest(email, password))
        }

        return safeApiCall<TokenResponse, GeneralError>(client, request)
    }

    @OptIn(InternalAPI::class)
    override suspend fun register(signUpRequest: SignUpRequest): Resource<TokenResponse> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url {
                protocol = URLProtocol.HTTPS
                host = Endpoints.BASE_URL
                encodedPath = "${Endpoints.auth}${Endpoints.register}"
            }
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(signUpRequest)
        }

        return safeApiCall<TokenResponse, GeneralError>(client, request)
    }
}