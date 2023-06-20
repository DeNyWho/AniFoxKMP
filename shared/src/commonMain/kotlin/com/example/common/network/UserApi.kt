package com.example.common.network

import com.example.common.core.wrapper.Resource
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.mangaResponse.light.MangaLight

interface UserApi {

    suspend fun getAnimeFavoriteList(
        pageNum: Int,
        pageSize: Int,
        token: String,
        status: String
    ): Resource<List<AnimeLight>>

    suspend fun getMangaFavoriteList(
        pageNum: Int,
        pageSize: Int,
        token: String,
        status: String
    ): Resource<List<MangaLight>>

    suspend fun setMangaFavoriteList(token: String, id: String, status: String): Resource<String>
    suspend fun setAnimeFavoriteList(token: String, url: String, status: String): Resource<String>
}