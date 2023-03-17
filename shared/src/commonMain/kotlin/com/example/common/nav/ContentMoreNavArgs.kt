package com.example.common.nav

import com.example.common.core.enum.ContentType
import com.example.common.core.enum.TypesOfMoreScreen
import kotlinx.serialization.Serializable

@Serializable
data class ContentMoreNavArgs(
    val typeOfScreen: TypesOfMoreScreen,
    val contentType: ContentType,
    val order: String? = null,
    val status: String? = null,
    val genres: String? = null
)