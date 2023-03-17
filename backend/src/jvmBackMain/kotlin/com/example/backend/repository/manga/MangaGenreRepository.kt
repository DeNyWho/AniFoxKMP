package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaGenre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MangaGenreRepository : JpaRepository<MangaGenre, String> {

    @Query("Select g from MangaGenre g where :title = g.title")
    fun findByTitle(title: String): Optional<MangaGenre>

    @Query("Select g from MangaGenre g")
    fun findAllGenreIDS(): List<MangaGenre>
}