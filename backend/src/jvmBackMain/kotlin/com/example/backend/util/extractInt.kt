package com.example.backend.util

import com.example.backend.jpa.manga.MangaChapters

fun extractInt(s: MangaChapters): Int {
    val num = s.title.replace("\\D".toRegex(), "")
    return if (num.isEmpty()) 0 else Integer.parseInt(num)
}