package com.example.backend.jpa.anime

import com.example.backend.jpa.user.UserFavorite
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "anime", schema = "anime")
@Cacheable(true)
data class AnimeTable (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val type: String = "",
    val url: String = "",
    val link: String = "",
    val title: String = "",
    val titleEn: String = "",
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "anime_otherTitles", schema = "anime")
    val otherTitles: MutableList<String> = mutableListOf(),
    val year: Int = 0,
    val episodesCount: Int = 0,
    val episodesAires: Int = 0,
    val shikimoriId: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val airedAt: LocalDate = LocalDate.now(),
    val releasedAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(
        name = "anime_translation",
        joinColumns = [JoinColumn(name = "anime_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "translation_id", referencedColumnName = "id")],
        schema = "anime",
    )
    var translation: MutableSet<AnimeTranslationTable> = mutableSetOf(),
    var status: String = "",
    @Column(columnDefinition = "TEXT")
    val description: String = "",
    val posterUrl: String = "",
    @ElementCollection(fetch = FetchType.LAZY)
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
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(
        name = "anime_media",
        joinColumns = [JoinColumn(name = "anime_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "media_id", referencedColumnName = "id")],
        schema = "anime",
    )
    var media: MutableSet<AnimeMediaTable> = mutableSetOf(),
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
    val season: String = "",
    val accentColor: String = "",
    @OneToMany(mappedBy = "anime", cascade = [CascadeType.ALL], orphanRemoval = true)
    val favorites: MutableSet<UserFavorite> = mutableSetOf()
) {
    fun addTranslationAll(translations: List<AnimeTranslationTable>): AnimeTable {
        translation.addAll(translations)
        return this
    }
    fun addTranslation(translations: AnimeTranslationTable): AnimeTable {
        translation.add(translations)
        return this
    }
    fun addMediaAll(mediaAll: List<AnimeMediaTable>): AnimeTable {
        media.addAll(mediaAll)
        return this
    }
    fun addMedia(mediaSingle: AnimeMediaTable): AnimeTable {
        media.add(mediaSingle)
        return this
    }
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
}