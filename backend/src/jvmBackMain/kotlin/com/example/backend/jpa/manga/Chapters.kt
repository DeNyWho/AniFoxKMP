package com.example.backend.jpa.manga

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "chapters", schema = "manga")
data class MangaChapters(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val urlCode: Int = 0,
    val date: LocalDate = LocalDate.now(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @JoinTable(schema = "manga")
    val mangaChaptersPage: MutableSet<MangaChaptersPage> = mutableSetOf()
) {
    fun addToMangaChaptersPage(chapter: MangaChaptersPage): MangaChapters {
        mangaChaptersPage.add(chapter)
        return this
    }
}
