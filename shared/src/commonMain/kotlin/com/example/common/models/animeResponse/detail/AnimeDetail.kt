@file:UseSerializers(LocalDateSerializer::class)
package com.example.common.models.animeResponse.detail

import com.example.common.models.animeResponse.common.AnimeGenres
import com.example.common.models.animeResponse.common.AnimeStudios
import com.example.common.models.common.ContentDetail
import com.example.common.util.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import kotlinx.serialization.Transient

@Serializable
data class AnimeDetail(
     val url: String = "",
     var title: String = "",
     var image: String = "",
     val studio: List<AnimeStudios> = listOf(),
     val season: String? = null,
     val description: String? = null,
     val otherTitles: List<String> = listOf(),
     val year: Int? = null,
     val linkPlayer: String? = null,
//     val releasedAt: LocalDate? = null,
//     val airedAt: LocalDate? = null,
     val type: String? = null,
     val episodesCount: Int? = null,
     val episodesCountAired: Int? = null,
     val genres: List<AnimeGenres> = listOf(),
     val status: String? = null,
     val ratingMpa: String? = null,
     val minimalAge: Int? = null
)

fun AnimeDetail.toContentDetail(): ContentDetail {
    return ContentDetail(
        url = url,
        title = title,
        image = image,
        studio = studio,
        season = season,
        description = description,
        otherTitles = otherTitles,
        year = year,
        linkPlayer = linkPlayer,
//        releasedAt = releasedAt,
//        airedAt = airedAt,
        type = type,
        episodesCount = episodesCount,
        episodesCountAired = episodesCountAired,
        genres = genres,
        status = status,
        ratingMpa = ratingMpa,
        minimalAge = minimalAge
    )
}