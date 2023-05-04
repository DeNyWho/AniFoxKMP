package com.example.backend.jpa.manga

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "genres", schema = "manga")
data class MangaGenre(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val title: String = ""
)