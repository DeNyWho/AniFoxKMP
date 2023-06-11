package com.example.common.models.animeResponse.common

import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.common.ContentLight
import com.example.common.models.common.GenresDetail
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class AnimeGenres(
    val id: String = UUID.randomUUID().toString(),
    val genre: String = ""
)

fun AnimeGenres.toGenresDetail(): GenresDetail {
    return GenresDetail(
        id = id,
        title = genre
    )
}