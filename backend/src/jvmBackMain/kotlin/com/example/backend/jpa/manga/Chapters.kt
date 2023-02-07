package com.example.backend.jpa.manga

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "chapters", schema = "manga")
data class MangaChapters(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val urlCode: Int = 0,
    val date: LocalDate = LocalDate.now()
)