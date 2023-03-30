package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeTable
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

    @Query("select distinct a.year from AnimeTable a")
    fun findDistinctByYear(): List<String>

    @Query("select a from AnimeTable a where ((:status) is null or a.status = :status)" +
            " and(:ratingMpa is null or a.ratingMpa = :ratingMpa)" +
            " and(:season is null or a.season = :season)" +
            " and(:minimalAge is null or a.minimalAge = :minimalAge)" +
            " and(:searchQuery is null or upper(a.title) like concat('%', upper(:searchQuery), '%'))"
    )
    fun findAnime(
        pageable: Pageable,
        @Param("status") status: String?,
        @Param("searchQuery") searchQuery: String?,
        @Param("ratingMpa") ratingMpa: String?,
        @Param("season") season: String?,
        @Param("minimalAge") minimalAge: Int?
    ): List<AnimeTable>
}