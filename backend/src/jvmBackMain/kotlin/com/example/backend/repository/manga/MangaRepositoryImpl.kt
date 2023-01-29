package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable

interface MangaRepositoryImpl {

    fun addDataToDB(): MangaTable
}