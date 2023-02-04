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
    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "manga_genres",
        joinColumns = [JoinColumn(name = "manga_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "genres_id", referencedColumnName = "id")],
        schema = "manga"
    )
    var genres: MutableSet<MangaGenre> = mutableSetOf(),
    @OneToOne(cascade = [CascadeType.ALL])
    var types: MangaTypes = MangaTypes(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @JoinTable(schema = "manga")
    var chapters: MutableSet<MangaChapters> = mutableSetOf(),
    var chaptersCount: Int = 0,
    val views: Int = 0,
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @JoinTable(schema = "manga")
    val rate: MutableSet<MangaRating> = mutableSetOf(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    var linked: MutableSet<MangaLinked> = mutableSetOf(),
    val updateTime: LocalDateTime = LocalDateTime.now()
){
    fun addMangaLinked(linkedTemp: MangaLinked): MangaTable{
        linked.add(linkedTemp)
        return this
    }

    fun addMangaRating(rating: MangaRating){
        rate.add(rating)
    }

    fun addMangaGenre(genreTemp: MangaGenre){
        genres.add(genreTemp)
    }

    fun addMangaChapters(chaptersTemp: List<MangaChapters>){
        chapters.addAll(chaptersTemp)
    }

}