package com.example.common.models.common

import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import com.example.common.models.mangaResponse.detail.GenresDetail
import com.example.common.models.mangaResponse.detail.TypesDetail
import java.time.LocalDate

data class ContentDetail(
    val id: String = "",
    var title: String = "",
    var image: String = "",
    val studio: List<AnimeStudios> = listOf(),
    val season: String = "",
    val otherTitles: List<String> = listOf(),
    val year: Int = 0,
    val releasedAt: LocalDate = LocalDate.now(),
    val airedAt: LocalDate = LocalDate.now(),
    val type: String = "",
    val episodesCount: Int = 0,
    val episodesCountAired: Int = 0,
    val status: String = "",
    val ratingMpa: String = "",
    val minimalAge: Int = 0,
    var url: String = "",
    var description: String = "",
    var genres: MutableSet<GenresDetail> = mutableSetOf(),
    var types: TypesDetail = TypesDetail(),
    var chaptersCount: Int = 0,
    val views: Int = 0
)