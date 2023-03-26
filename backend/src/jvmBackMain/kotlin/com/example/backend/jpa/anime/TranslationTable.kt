package com.example.backend.jpa.anime

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*


@Entity
@Table(name = "translation", schema = "anime")
data class TranslationTable(
    @Id
    val id: Int = 0,
    val title: String = "",
    val voice: String = ""
)