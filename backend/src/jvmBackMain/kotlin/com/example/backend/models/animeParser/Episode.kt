package com.example.backend.models.animeParser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("link")
    val link: String,
    @SerialName("screenshots")
    val screenshots: List<String>
)