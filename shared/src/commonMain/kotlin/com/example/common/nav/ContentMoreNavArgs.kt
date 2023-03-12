package com.example.common.nav

import com.example.common.core.enum.ContentType
import kotlinx.serialization.Serializable

@Serializable
data class ContentMoreNavArgs(
    val contentType: ContentType,
    val order: String? = null,
    val status: String? = null,
    val genres: String? = null
)