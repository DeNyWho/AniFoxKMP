package com.example.common.nav

import com.example.common.core.enum.ContentType
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailsNavArgs(
    val id: String,
    val contentType: ContentType
)