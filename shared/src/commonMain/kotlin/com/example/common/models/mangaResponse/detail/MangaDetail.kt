package com.example.common.models.mangaResponse.detail

import com.example.common.models.common.ContentDetail
import com.example.common.models.common.GenresDetail
import kotlinx.serialization.Serializable

@Serializable
data class MangaDetail(
    val id: String = "",
    var title: String = "",
    var image: String = "",
    var url: String = "",
    var description: String? = "",
    var genres: MutableSet<GenresDetail> = mutableSetOf(),
    var types: TypesDetail = TypesDetail(),
    var chaptersCount: Int = 0,
    val views: Int = 0
)

fun MangaDetail.toContentDetail(): ContentDetail {
    return ContentDetail(
        title = title,
        image = image,
        url = id,
        description = description,
        genres = genres.toList(),
        type = types.type,
        status = types.status,
        year = types.year,
        minimalAge = if(types.limitation != "") types.limitation?.replace("+","")?.replace("-","")?.toInt() else 0,
        episodesCount = chaptersCount
    )
}