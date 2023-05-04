package com.example.backend.models.animeResponse.media

import com.example.backend.jpa.anime.AnimeMediaTable

data class AnimeMediaResponse(
    val url: String = "",
    val imageUrl: String = "",
    val playerUrl: String = "",
    val name: String = "",
    val kind: String = "",
    val hosting: String = ""
) {
    companion object {
        fun fromAnimeMediaTable(animeMediaTable: AnimeMediaTable): AnimeMediaResponse {
            return AnimeMediaResponse(
                url = animeMediaTable.url,
                imageUrl = animeMediaTable.imageUrl,
                playerUrl = animeMediaTable.playerUrl,
                name = animeMediaTable.name,
                kind = animeMediaTable.kind,
                hosting = animeMediaTable.hosting
            )
        }
    }
}