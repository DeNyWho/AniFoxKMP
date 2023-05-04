package com.example.backend.jpa.manga

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "chapters_page", schema = "manga")
data class MangaChaptersPage(
    @Id
    val id: String? = UUID.randomUUID().toString(),
    val imagePageUrl: String = ""
)