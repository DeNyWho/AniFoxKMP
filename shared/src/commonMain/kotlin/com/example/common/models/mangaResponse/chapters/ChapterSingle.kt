package com.example.common.models.mangaResponse.chapters

import kotlinx.serialization.Serializable


@Serializable
data class ChapterSingle(
    val images: List<String> = listOf()
)