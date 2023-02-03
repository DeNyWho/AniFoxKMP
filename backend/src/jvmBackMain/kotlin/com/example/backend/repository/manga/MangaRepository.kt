package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable
import com.example.backend.models.MangaDetailRequestID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MangaRepository: JpaRepository<MangaTable, String> {

    @Query("Select m From MangaTable m where m.url = :url")
    fun mangaByUrl(url: String): Optional<MangaTable>

    @Query("Select m.id, m.title, m.image, m.url, m.description, m.genres, m.types, m.chaptersCount, m.views From MangaTable m where m.id = :id")
    fun findMangaDetailById(@Param("id") id: String): Optional<MangaDetailRequestID>
    
    fun <VIEW> findOneById(id: String): VIEW?

    @Query("Select m From MangaTable m where :genre member of m.genres")
    fun mangaByUrlz(genre: String): Optional<MangaTable>
}