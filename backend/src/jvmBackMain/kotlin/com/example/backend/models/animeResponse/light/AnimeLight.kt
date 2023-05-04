package com.example.backend.models.animeResponse.light

import com.example.backend.jpa.anime.AnimeGenreTable
import com.example.backend.jpa.anime.AnimeStudiosTable
import kotlinx.serialization.Serializable

@Serializable
data class AnimeLight(
    val url: String = "",
    var title: String = "",
    var image: String = "",
    var type: String = "",
    val studio: List<AnimeStudiosTable> = listOf(),
    val season: String = "",
    val year: Int = 0,
    val episodesCount: Int = 0,
    val genres: List<AnimeGenreTable> = listOf(),
    val status: String = "",
    val ratingMpa: String = "",
    val minimalAge: Int = 0,
    val accentColor: String = "",
)