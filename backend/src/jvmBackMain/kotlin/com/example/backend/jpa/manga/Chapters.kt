package com.example.backend.jpa.manga

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "chapters", schema = "manga")
data class MangaChapters(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val urlCode: Int = 0,
    val date: LocalDate = LocalDate.now()
)