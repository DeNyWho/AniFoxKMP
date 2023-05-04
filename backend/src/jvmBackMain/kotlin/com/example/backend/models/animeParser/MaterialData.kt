@file:UseSerializers(LocalDateSerializer::class)
package com.example.backend.models.animeParser

import com.example.backend.util.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class MaterialData(
    @SerialName("anime_title")
    val title: String = "",
    @SerialName("title_en")
    val titleEn: String = "",
    @SerialName("other_titles")
    val otherTitles: List<String> = listOf(),
    @SerialName("anime_status")
    val animeStatus: String = "",
    @SerialName("year")
    val year: Int = 0,
    @SerialName("anime_kind")
    val animeType: String = "",
    @SerialName("anime_description")
    val description: String = "",
    @SerialName("poster_url")
    val poster: String = "",
    @SerialName("screenshots")
    val screenShots: List<String> = listOf(),
    @SerialName("anime_genres")
    val genres: List<String> = listOf(),
    @SerialName("anime_studios")
    val animeStudios: List<String> = listOf(),
    @SerialName("shikimori_rating")
    val shikimoriRating: Double = 0.0,
    @SerialName("shikimori_votes")
    val shikimoriVotes: Int = 0,
    @SerialName("released_at")
    val releasedAt: LocalDate = LocalDate.now(),
    @SerialName("aired_at")
    val airedAt: LocalDate = LocalDate.now(),
    @SerialName("rating_mpaa")
    val ratingMpa: String = "",
    @SerialName("episodes_total")
    val episodesTotal: Int = 0,
    @SerialName("episodes_aired")
    val episodesAired: Int = 0,
    @SerialName("minimal_age")
    val minimalAge: Int = 0,

)