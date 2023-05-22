package com.example.common.models.mangaResponse.light

import com.example.common.models.common.ContentLight
import kotlinx.serialization.Serializable

@Serializable
data class MangaLight(
    val url: String = "",
    var title: String = "",
    var image: String = ""
)

fun MangaLight.toContentLight(): ContentLight {
    return ContentLight(
        url = url,
        title = title,
        image = image
    )
}
