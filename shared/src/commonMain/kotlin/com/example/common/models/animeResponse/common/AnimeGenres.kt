package com.example.common.models.animeResponse.common

import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class AnimeGenres(
    val id: String = UUID.randomUUID().toString(),
    val genre: String = ""
)