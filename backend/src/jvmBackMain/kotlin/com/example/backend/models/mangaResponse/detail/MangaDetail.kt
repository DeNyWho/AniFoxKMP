package com.example.backend.models.mangaResponse.detail

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