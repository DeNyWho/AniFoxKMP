package com.example.backend.jpa.manga

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "chapters", schema = "manga")
data class MangaChapters(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = null,
    val title: String = "",
    val urlCode: Int = 0,
    val date: LocalDate = LocalDate.now(),
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.ALL]
    )
    @JoinTable(schema = "manga")
    val mangaChaptersPage: MutableSet<MangaChaptersPage> = mutableSetOf()
) {
    fun addToMangaChaptersPage(chapter: MangaChaptersPage): MangaChapters {
        mangaChaptersPage.add(chapter)
        return this
    }
}
