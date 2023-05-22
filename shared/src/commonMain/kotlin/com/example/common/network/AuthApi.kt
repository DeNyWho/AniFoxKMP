package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.response.ServiceResponse

interface AuthApi {

    suspend fun login(email: String, password: String): Resource<ServiceResponse<String?>>
}