package com.example.common.models.animeResponse.light

import kotlinx.serialization.Serializable


@Serializable
data class AnimeLight(
    val id: String = "",
    var title: String = "",
    var image: String = ""
)