package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaGenre
import com.example.backend.jpa.manga.MangaTable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MangaRepository: JpaRepository<MangaTable, String> {

    @Query("Select m From MangaTable m where m.url = :url")
    fun mangaByUrl(url: String): Optional<MangaTable>

    fun findMangaTableByGenresIn(pageable: Pageable, @Param("genre") genres: List<MangaGenre>): List<MangaTable>
    @Query("select m from MangaTable m join m.genres g where g in (:genre) order by random()")
    fun findMangaTableByGenresInRandom(pageable: Pageable, @Param("genre") genres: List<MangaGenre>): List<MangaTable>
    @Query("select m from MangaTable m join m.genres g where g in (:genre) and m.types.status = :status order by random()")
    fun findMangaTableByGenresAndStatusInRandom(pageable: Pageable, @Param("genre") genres: List<MangaGenre>, @Param("status") status: String): List<MangaTable>
    @Query("select m from MangaTable m join m.genres g where g in (:genre) and m.types.status = :status")
    fun findMangaTableByGenresAndStatusIn(pageable: Pageable, @Param("genre") genres: List<MangaGenre>, @Param("status") status: String): List<MangaTable>
    @Query("select m from MangaTable m where m.types.status = :status order by random()")
    fun findMangaTableByStatusRandom(pageable: Pageable, @Param("status") status: String): List<MangaTable>
    @Query("select m from MangaTable m where m.types.status = :status")
    fun findMangaTableByStatus(pageable: Pageable, @Param("status") status: String): List<MangaTable>
    @Query("select m from MangaTable m order by random()")
    fun findMangaTableByRandom(pageable: Pageable): List<MangaTable>

    @Query("select m from MangaTable m where upper(m.title) like concat('%', upper(?1), '%')")
    fun findByTitleSearch(pageable: Pageable, @Param("title") title: String): Page<MangaTable>

}