package com.example.common.nav

import com.example.common.models.common.ContentMedia
import kotlinx.serialization.Serializable

@Serializable
data class ContentScreenShotsNavArgs(
    val contentScreenShots: List<ContentMedia> = listOf()
)