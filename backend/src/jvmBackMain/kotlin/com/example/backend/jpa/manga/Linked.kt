package com.example.backend.jpa.manga

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "linked", schema = "manga")
data class MangaLinked(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val mangaID: String = ""
)