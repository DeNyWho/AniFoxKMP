package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.auth.SignUpRequest
import com.example.common.models.auth.TokenResponse
import com.example.common.models.response.ServiceResponse

interface AuthApi {

    suspend fun login(email: String, password: String): Resource<TokenResponse>

    suspend fun register(signUpRequest: SignUpRequest): Resource<TokenResponse>
}