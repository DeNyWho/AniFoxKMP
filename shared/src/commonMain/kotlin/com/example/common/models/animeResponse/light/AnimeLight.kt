package com.example.common.models.animeResponse.light

import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import com.example.common.models.common.ContentLight
import kotlinx.serialization.Serializable


@Serializable
data class AnimeLight(
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

fun AnimeLight.toContentLight(): ContentLight {
    return ContentLight(
        url = url,
        title = title,
        image = image,
        type = type,
        studio = studio,
        season = season,
        year = year,
        episodesCount = episodesCount,
        genres = genres,
        status = status,
        ratingMpa = ratingMpa,
        minimalAge = minimalAge,
        accentColor = accentColor,
    )
}