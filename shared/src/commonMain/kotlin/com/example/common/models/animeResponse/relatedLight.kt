package com.example.common.models.animeResponse

import com.example.common.models.animeResponse.light.AnimeLight
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRelatedLight(
    val anime: AnimeLight
)