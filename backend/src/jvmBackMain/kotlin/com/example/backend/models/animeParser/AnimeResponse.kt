package com.example.backend.models.animeParser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponse(
    @SerialName("time")
    val time: String = "",
    @SerialName("next_page")
    val nextPage: String? = null,
    @SerialName("results")
    val result: List<AnimeParser> = listOf(),
)