package com.example.backend.jpa.anime

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "anime", schema = "anime")
data class AnimeTable (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val type: String = "",
    val link: String = "",
    val title: String = "",
    @ElementCollection
    @CollectionTable(name = "anime_otherTitles", schema = "anime")
    val otherTitles: MutableList<String> = mutableListOf(),
    val year: Int = 0,
    val episodesCount: Int = 0,
    val episodesAires: Int = 0,
    val shikimoriId: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val airedAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(
        name = "anime_seasons",
        joinColumns = [JoinColumn(name = "anime_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "season_id", referencedColumnName = "id")],
        schema = "anime"
    )
    val seasons: MutableSet<AnimeSeasonTable> = mutableSetOf(),
    val status: String = "",
    @Column(columnDefinition = "TEXT")
    val description: String = "",
    val posterUrl: String = "",
    @ElementCollection
    @CollectionTable(name = "anime_screenshots", schema = "anime")
    val screenshots: MutableList<String> = mutableListOf(),
    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(
        name = "anime_genres",
        joinColumns = [JoinColumn(name = "anime_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")],
        schema = "anime",
    )
    var genres: MutableSet<AnimeGenreTable> = mutableSetOf(),
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
    var studios: MutableSet<AnimeStudiosTable> = mutableSetOf(),
    val shikimoriRating: Double = 0.0,
    val shikimoriVotes: Int = 0,
    val ratingMpa: String = "",
    val minimalAge: Int = 0,
) {
    fun addAnimeGenre(genre: AnimeGenreTable): AnimeTable {
        genres.add(genre)
        return this
    }
    fun addAllAnimeGenre(genre: List<AnimeGenreTable>): AnimeTable {
        genres.addAll(genre)
        return this
    }
    fun addAnimeStudios(studio: AnimeStudiosTable): AnimeTable {
        studios.add(studio)
        return this
    }
    fun addAllAnimeStudios(studio: List<AnimeStudiosTable>): AnimeTable {
        studios.addAll(studio)
        return this
    }
    fun addSeason(season: List<AnimeSeasonTable>): AnimeTable{
        seasons.addAll(season)
        return this
    }
}