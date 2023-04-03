package com.example.common.models.mangaResponse.detail

import com.example.common.models.common.ContentDetail
import kotlinx.serialization.Serializable

@Serializable
data class MangaDetail(
    val id: String = "",
    var title: String = "",
    var image: String = "",
    var url: String = "",
    var description: String = "",
    var genres: MutableSet<GenresDetail> = mutableSetOf(),
    var types: TypesDetail = TypesDetail(),
    var chaptersCount: Int = 0,
    val views: Int = 0
)

fun MangaDetail.toContentDetail(): ContentDetail {
    return ContentDetail(
        id = id,
        title = title,
        image = image,
        url = url,
        description = description,
        genres = genres,
        types = types,
        chaptersCount = chaptersCount,
        views = views
    )
}