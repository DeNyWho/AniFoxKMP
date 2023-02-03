package com.example.backend.models

import com.example.backend.jpa.manga.MangaChapters
import com.example.backend.jpa.manga.MangaGenre
import com.example.backend.jpa.manga.MangaTypes

data class MangaDetailRequestID(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val url: String = "",
    val description: String = "",
    val genres: MutableSet<MangaGenre> = mutableSetOf(),
    val types: MangaTypes = MangaTypes(),
    val chapters: MutableSet<MangaChapters> = mutableSetOf(),
    val chaptersCount: Int = 0,
    val views: Int = 0,
)