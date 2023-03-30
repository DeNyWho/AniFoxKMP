package com.example.backend.jpa.anime

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.serialization.Serializable
import java.util.*


@Entity
@Table(name = "genres", schema = "anime")
@Serializable
data class AnimeGenreTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val genre: String = ""
)