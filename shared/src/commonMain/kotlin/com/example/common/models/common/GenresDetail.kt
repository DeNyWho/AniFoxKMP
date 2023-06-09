package com.example.common.models.common

import kotlinx.serialization.Serializable

@Serializable
data class GenresDetail(
    val id: String = "",
    val title: String = ""
)