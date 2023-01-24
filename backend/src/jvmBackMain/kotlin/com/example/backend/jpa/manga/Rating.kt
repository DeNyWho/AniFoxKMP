package com.example.backend.jpa.manga

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "rating", schema = "manga")
data class MangaRating(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val rate: Double = 0.0,
    @Column(columnDefinition = "TEXT")
    val comment: String = "",
)