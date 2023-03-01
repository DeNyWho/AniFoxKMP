package com.example.common.nav

import kotlinx.serialization.Serializable

@Serializable
data class ContentMorePageNavArgs(
    val page: Int = 0,
    val pageSize: Int = 48,
    val genres: List<String>? = null,
    val title: String = "",
    val type: String = ""
)