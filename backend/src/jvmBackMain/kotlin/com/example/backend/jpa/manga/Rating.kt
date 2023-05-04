package com.example.backend.jpa.manga

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rating", schema = "manga")
data class MangaRating(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = UUID.randomUUID(),
    val rate: Double = 0.0,
    @Column(columnDefinition = "TEXT")
    val comment: String = "",
)