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
        id = id,
        title = title,
        image = image
    )
}
