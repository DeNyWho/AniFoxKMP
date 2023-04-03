@file:UseSerializers(LocalDateSerializer::class)
package com.example.common.models.animeResponse.detail

import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import com.example.common.models.common.ContentDetail
import com.example.common.util.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class AnimeDetail(
    val id: String = "",
    var title: String = "",
    var image: String = "",
    val studio: List<AnimeStudios> = listOf(),
    val season: String = "",
    val description: String = "",
    val otherTitles: List<String> = listOf(),
    val year: Int = 0,
    val releasedAt: LocalDate = LocalDate.now(),
    val airedAt: LocalDate = LocalDate.now(),
    val type: String = "",
    val episodesCount: Int = 0,
    val episodesCountAired: Int = 0,
    val genres: List<AnimeGenres> = listOf(),
    val status: String = "",
    val ratingMpa: String = "",
    val minimalAge: Int = 0
)

fun AnimeDetail.toContentDetail(): ContentDetail {
    return ContentDetail(
        id = id,
        title = title,
        image = image,
        studio = studio,
        season = season,
        otherTitles = otherTitles,
        year = year,
        releasedAt = releasedAt,
        airedAt = airedAt,
        type = type,
        episodesCount = episodesCount,
        episodesCountAired = episodesCountAired,
        status = status,
        ratingMpa = ratingMpa
    )
}