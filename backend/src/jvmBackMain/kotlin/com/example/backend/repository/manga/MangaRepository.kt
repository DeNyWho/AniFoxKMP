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

    @Query("select m from MangaTable m join m.genres g where g in (:genres) and ((:status) is null or m.types.status = :status )" +
            " and(:status is null or m.types.status = :status)"
    )
    fun findMangaWithGenre(pageable: Pageable, @Param("genres") genres: List<MangaGenre>?, @Param("status") status: String?): List<MangaTable>

    @Query("select m from MangaTable m where ((:status) is null or m.types.status = :status )" +
            " and(:status is null or m.types.status = :status)"
    )
    fun findMGenres(pageable: Pageable, @Param("status") status: String?): List<MangaTable>

    @Query("select m from MangaTable m where ((:status) is null or m.types.status = :status )" +
            " and(:status is null or m.types.status = :status)" +
            " and(:searchQuery is null or upper(m.title) like concat('%', upper(:searchQuery), '%'))"
    )
    fun findManga(pageable: Pageable, @Param("status") status: String?, @Param("searchQuery") searchQuery: String?): List<MangaTable>
}