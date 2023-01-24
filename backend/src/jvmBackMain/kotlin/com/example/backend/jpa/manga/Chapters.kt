package com.example.backend.jpa.manga

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "chapters", schema = "manga")
data class MangaChapters(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val url: String = "",
    val date: LocalDateTime = LocalDateTime.now()
)