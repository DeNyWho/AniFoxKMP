package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaChapters
import com.example.backend.jpa.manga.MangaTable
import com.example.backend.models.MangaDetailRequestID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MangaRepository: JpaRepository<MangaTable, String> {

    @Query("Select m From MangaTable m where m.url = :url")
    fun mangaByUrl(url: String): Optional<MangaTable>

    @Query("Select m From MangaTable m inner join m.chapters where m.id = :id")
    fun mangaChapters(pageable: Pageable, @Param("id") id: String): List<MangaChapters>

    @Query("select m from MangaTable m where upper(m.title) like concat('%', upper(?1), '%')")
    fun findByTitleSearch(pageable: Pageable, @Param("title") title: String): Page<MangaTable>

}