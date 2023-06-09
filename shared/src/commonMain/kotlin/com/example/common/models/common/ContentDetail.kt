package com.example.common.models.common

import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import java.time.LocalDate

data class ContentDetail(
    val url: String = "",
    var title: String = "",
    var image: String = "",
    val studio: List<AnimeStudios> = listOf(),
    val season: String? = null,
    val description: String? = null,
    val otherTitles: List<String> = listOf(),
    val year: Int? = null,
    val linkPlayer: String? = null,
    val releasedAt: LocalDate? = LocalDate.now(),
    val airedAt: LocalDate? = LocalDate.now(),
    val type: String? = null,
    val episodesCount: Int? = null,
    val episodesCountAired: Int? = null,
    val genres: List<GenresDetail> = listOf(),
    val status: String? = null,
    val ratingMpa: String? = null,
    val minimalAge: Int? = null,
)