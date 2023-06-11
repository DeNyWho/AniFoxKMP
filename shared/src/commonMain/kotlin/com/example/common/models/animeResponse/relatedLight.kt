package com.example.common.models.animeResponse

import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.common.ContentLight
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRelatedLight(
    val anime: AnimeLight
)

fun AnimeRelatedLight.toContentLight(): ContentLight {
    return ContentLight(
        url = anime.url,
        title = anime.title,
        image = anime.image,
        type = anime.type,
        studio = anime.studio,
        season = anime.season,
        year = anime.year,
        episodesCount = anime.episodesCount,
        genres = anime.genres,
        status = anime.status,
        ratingMpa = anime.ratingMpa,
        minimalAge = anime.minimalAge,
        accentColor = anime.accentColor,
    )
}