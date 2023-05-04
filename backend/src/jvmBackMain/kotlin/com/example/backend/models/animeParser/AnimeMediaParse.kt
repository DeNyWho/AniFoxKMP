package com.example.backend.models.animeParser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeMediaParse(
    @SerialName("videos")
    val videos: List<AnimeVideoParse> = listOf()
)