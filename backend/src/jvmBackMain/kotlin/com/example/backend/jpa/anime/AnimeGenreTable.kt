package com.example.backend.jpa.anime

import kotlinx.serialization.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "genres", schema = "anime")
@Serializable
data class AnimeGenreTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val genre: String = ""
)