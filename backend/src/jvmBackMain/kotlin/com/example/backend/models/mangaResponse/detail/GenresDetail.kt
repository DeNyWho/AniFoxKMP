package com.example.backend.models.mangaResponse.detail

import kotlinx.serialization.Serializable

@Serializable
data class GenresDetail(
    val id: String = "",
    val title: String = ""
)