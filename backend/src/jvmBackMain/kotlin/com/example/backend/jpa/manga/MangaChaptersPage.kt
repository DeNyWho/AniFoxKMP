package com.example.backend.jpa.manga

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "chapters_page", schema = "manga")
data class MangaChaptersPage(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val imagePageUrl: String = ""
)