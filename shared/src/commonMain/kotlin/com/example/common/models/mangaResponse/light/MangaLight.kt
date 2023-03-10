package com.example.common.models.mangaResponse.light

import kotlinx.serialization.Serializable

@Serializable
data class MangaLight(
    val id: String = "",
    var title: String = "",
    var image: String = ""
)
