package com.example.backend.jpa.manga

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "genres", schema = "manga")
data class MangaGenre(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = ""
)