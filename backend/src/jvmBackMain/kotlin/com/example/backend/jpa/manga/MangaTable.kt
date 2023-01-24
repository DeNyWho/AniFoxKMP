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
    val url: String = "",
    @Column(columnDefinition = "TEXT")
    val description: String = "",
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    val genres: MutableSet<MangaGenre> = mutableSetOf(),
    @OneToOne(cascade = [CascadeType.ALL])
    val types: MangaTypes = MangaTypes(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    val chapters: MutableSet<MangaChapters> = mutableSetOf(),
    val chaptersCount: Int = 0,
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
    val linked: MutableSet<MangaLinked> = mutableSetOf(),
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