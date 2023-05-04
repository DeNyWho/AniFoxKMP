package com.example.backend.models.animeResponse.media

import com.example.backend.jpa.anime.AnimeStudiosTable

data class AnimeSeasons(
    val link: String = "",
    val season: String = "",
    val translation: MutableSet<AnimeStudiosTable> = mutableSetOf(),
    val episodes: MutableList<AnimeEpisodes> = mutableListOf()
)