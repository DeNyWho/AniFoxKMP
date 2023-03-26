package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeTable
import com.example.backend.jpa.manga.MangaTable
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnimeRepository : JpaRepository<AnimeTable, String> {

    @Query("select a from AnimeTable a where :title = a.title")
    fun findByTitle(title: String): Optional<AnimeTable>

    @Query("select a from AnimeTable a where ((:status) is null or a.status = :status)" +
            " and(:searchQuery is null or upper(a.title) like concat('%', upper(:searchQuery), '%'))"
    )
    fun findAnime(pageable: Pageable, @Param("status") status: String?, @Param("searchQuery") searchQuery: String?): List<AnimeTable>
}