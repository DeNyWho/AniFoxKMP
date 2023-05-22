package com.example.common.models.common

import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import kotlinx.serialization.Serializable

@Serializable
data class ContentLight(
    val url: String = "",
    var title: String = "",
    var image: String = "",
    var type: String = "",
    val studio: List<AnimeStudios> = listOf(),
    val season: String = "",
    val year: Int = 0,
    val episodesCount: Int = 0,
    val genres: List<AnimeGenres> = listOf(),
    val status: String = "",
    val ratingMpa: String = "",
    val minimalAge: Int = 0,
    val accentColor: String = "",
)