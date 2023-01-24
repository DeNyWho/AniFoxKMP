package com.example.backend.jpa.manga

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "types", schema = "manga")
data class MangaTypes(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val type: String = "",
    val year: String = "",
    val status: String = "",
    val limitation: String = ""
)