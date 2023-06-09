package com.example.common.models.mangaResponse.light

import com.example.common.models.common.ContentLight
import kotlinx.serialization.Serializable

@Serializable
data class MangaLight(
    val id: String = "",
    var title: String = "",
    var image: String = ""
)

fun MangaLight.toContentLight(): ContentLight {
    return ContentLight(
        url = id,
        title = title,
        image = image
    )
}
