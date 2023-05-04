package com.example.backend.jpa.manga

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "types", schema = "manga")
data class MangaTypes(
    @Id
    val id: String? = UUID.randomUUID().toString(),
    val type: String = "",
    @Column(nullable = true)
    val year: Int? = null,
    @Column(nullable = true)
    val status: String? = null,
    @Column(nullable = true)
    val limitation: String? = null
)