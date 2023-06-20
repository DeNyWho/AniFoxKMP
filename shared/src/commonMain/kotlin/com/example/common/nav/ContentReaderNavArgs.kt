package com.example.common.nav

import kotlinx.serialization.Serializable

@Serializable
data class ContentReaderNavArgs(
    val mangaID: String,
    val chapterId: String,
    val chapterCode: String,
    val chapterTitle: String
)

@Serializable
data class ContentChaptersNavArgs(
    val mangaID: String
)