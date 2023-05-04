package com.example.backend.models.animeResponse.media

import com.example.backend.jpa.anime.AnimeTranslationTable

data class AnimeEpisodes(
    val episodeNumber: Int = 0,
    val link: String = "",
    val translation: MutableSet<AnimeTranslationTable> = mutableSetOf(),
    val screenshots: MutableList<String> = mutableListOf()
)