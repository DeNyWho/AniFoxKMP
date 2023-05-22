package com.example.common.models.common

import kotlinx.serialization.Serializable

@Serializable
data class ContentMedia(
    val url: String = "",
    val imageUrl: String = "",
    val playerUrl: String = "",
    val name: String = "",
    val kind: String = "",
    val hosting: String = ""
)