package com.example.backend.jpa.anime

import kotlinx.serialization.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "media", schema = "anime")
@Serializable
data class AnimeMediaTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val url: String = "",
    val imageUrl: String = "",
    val playerUrl: String = "",
    val name: String = "",
    val kind: String = "",
    val hosting: String = "",
)