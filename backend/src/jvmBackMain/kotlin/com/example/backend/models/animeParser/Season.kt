package com.example.backend.models.animeParser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Season(
    @SerialName("link")
    val link: String = "",
    @SerialName("episodes")
    val episodes: Map<String, Episode>
)