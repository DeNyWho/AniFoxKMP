package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MangaRepository: JpaRepository<MangaTable, String> {

    @Query("Select m From MangaTable m where m.url = :url")
    fun mangaByUrl(url: String): Optional<MangaTable>
}