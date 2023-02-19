package com.example.common.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreRequest(
    @SerialName("genres")
    val genres: List<String>? = listOf()
)