package com.example.backend.jpa.anime

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "episodes", schema = "anime")
data class AnimeEpisodeTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val link: String = "",
    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(
        name = "anime_translation",
        joinColumns = [JoinColumn(name = "episode_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "translation_id", referencedColumnName = "id")],
        schema = "anime",
    )
    var translation: MutableSet<AnimeTranslationTable> = mutableSetOf(),
    @ElementCollection
    @CollectionTable(name = "episode_screenshots", schema = "anime")
    val screenshots: MutableList<String> = mutableListOf()
) {
    fun addTranslationToEpisode(translations: List<AnimeTranslationTable>): AnimeEpisodeTable {
        translation.addAll(translations)
        return this
    }
}