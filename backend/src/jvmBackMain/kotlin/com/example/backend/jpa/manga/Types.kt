package com.example.backend.jpa.manga

import jakarta.persistence.Column
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
    @Column(nullable = true)
    val year: Int? = null,
    @Column(nullable = true)
    val status: String? = null,
    @Column(nullable = true)
    val limitation: String? = null
)