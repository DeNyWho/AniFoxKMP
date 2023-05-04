package com.example.backend.jpa.anime

import kotlinx.serialization.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "studios", schema = "anime")
@Serializable
data class AnimeStudiosTable(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val studio: String = ""
)