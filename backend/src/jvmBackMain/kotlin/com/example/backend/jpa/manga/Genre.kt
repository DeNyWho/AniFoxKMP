package com.example.backend.jpa.manga

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "genres", schema = "manga")
data class MangaGenre(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = ""
)