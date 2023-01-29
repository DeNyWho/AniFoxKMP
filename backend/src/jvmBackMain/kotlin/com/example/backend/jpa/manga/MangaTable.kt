package com.example.backend.jpa.manga

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "manga", schema = "manga")
data class MangaTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var image: String = "",
    var url: String = "",
    @Column(columnDefinition = "TEXT")
    var description: String = "",
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    var genres: MutableSet<MangaGenre> = mutableSetOf(),
    @OneToOne(cascade = [CascadeType.ALL])
    var types: MangaTypes = MangaTypes(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    var chapters: MutableSet<MangaChapters> = mutableSetOf(),
    var chaptersCount: Int = 0,
    val views: Int = 0,
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    val rate: MutableSet<MangaRating> = mutableSetOf(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    var linked: MutableSet<MangaLinked> = mutableSetOf(),
    val updateTime: LocalDateTime = LocalDateTime.now()
){
    fun addMangaLinked(linkedTemp: List<MangaLinked>){
        linked.addAll(linkedTemp)
    }

    fun addMangaRating(rating: MangaRating){
        rate.add(rating)
    }

    fun addMangaChapters(chaptersTemp: List<MangaChapters>){
        chapters.addAll(chaptersTemp)
    }

    fun addMangaGenres(genresTemp: List<MangaGenre>){
        genres.addAll(genresTemp)
    }


}