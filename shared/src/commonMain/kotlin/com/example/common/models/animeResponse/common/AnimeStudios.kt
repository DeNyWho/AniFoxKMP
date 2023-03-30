package com.example.common.models.animeResponse.common

import kotlinx.serialization.Serializable

@Serializable
data class AnimeStudios(
    val id: String = "",
    val studio: String = ""
)