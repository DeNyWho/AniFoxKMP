package com.example.backend.jpa.anime

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table (name = "season", schema = "anime")
data class AnimeSeasonTable (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val link: String = "",
    val season: String = "",
    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(
        name = "anime_studios",
        joinColumns = [JoinColumn(name = "anime_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "studio_id", referencedColumnName = "id")],
        schema = "anime",
    )
    var translation: MutableSet<AnimeStudiosTable> = mutableSetOf(),
    @OneToMany (
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable (
        name = "anime_episodes",
        joinColumns = [JoinColumn(name = "season_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "episode_id", referencedColumnName = "id")],
        schema = "anime",
    )
    val episodes: MutableSet<AnimeEpisodeTable> = mutableSetOf(),
) {
    fun addEpisodes(episode: List<AnimeEpisodeTable>): AnimeSeasonTable {
        episodes.addAll(episode)
        return this
    }
}