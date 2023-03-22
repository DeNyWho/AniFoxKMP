package com.example.backend.jpa.manga

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "rating", schema = "manga")
data class MangaRating(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val rate: Double = 0.0,
    @Column(columnDefinition = "TEXT")
    val comment: String = "",
)