package com.example.backend.jpa.manga

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*


@Entity
@Table(name = "chapters_page", schema = "manga")
data class MangaChaptersPage(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val imagePageUrl: String = ""
)