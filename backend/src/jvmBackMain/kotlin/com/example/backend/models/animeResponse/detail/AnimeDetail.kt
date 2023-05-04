@file:UseSerializers(LocalDateSerializer::class)
package com.example.backend.models.animeResponse.detail

import com.example.backend.jpa.anime.AnimeGenreTable
import com.example.backend.jpa.anime.AnimeStudiosTable
import com.example.backend.util.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate


@Serializable
data class AnimeDetail(
    val url: String = "",
    var title: String = "",
    var image: String = "",
    val studio: List<AnimeStudiosTable> = listOf(),
    val season: String = "",
    val description: String = "",
    val otherTitles: List<String> = listOf(),
    val year: Int = 0,
    val linkPlayer: String = "",
    val releasedAt: LocalDate = LocalDate.now(),
    val airedAt: LocalDate = LocalDate.now(),
    val type: String = "",
    val episodesCount: Int = 0,
    val episodesCountAired: Int = 0,
    val genres: List<AnimeGenreTable> = listOf(),
    val status: String = "",
    val ratingMpa: String = "",
    val minimalAge: Int = 0,
)