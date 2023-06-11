package com.example.common.network

import com.example.common.core.enum.StatusListType
import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.response.ServiceResponse

interface UserApi {

    suspend fun getAnimeFavoriteList(
        pageNum: Int,
        pageSize: Int,
        token: String,
        status: StatusListType
    ): Resource<ServiceResponse<AnimeLight>>
}