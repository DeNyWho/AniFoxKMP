package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnimeRepository : JpaRepository<AnimeTable, String> {


    fun findByTitle(title: String): Optional<AnimeTable>

    fun findByShikimoriId(shikimoriID: String): Optional<AnimeTable>

    @Query("Select distinct a.year from AnimeTable a order by a.year desc")
    fun findDistinctByYear(): List<String>

    fun findByPosterUrl(posterUrl: String): Optional<AnimeTable>

    @Query("SELECT a FROM AnimeTable a LEFT JOIN FETCH a.translation t WHERE a.title = :title")
    fun findByTitleWithTranslation(@Param("title") title: String): Optional<AnimeTable>

    @Query("SELECT a FROM AnimeTable a LEFT JOIN FETCH a.translation t WHERE a.shikimoriId = :shikimoriID")
    fun findByShikimoriIdWithTranslation(@Param("shikimoriID") shikimoriID: String): Optional<AnimeTable>

    @Query("SELECT a FROM AnimeTable a LEFT JOIN FETCH a.otherTitles o WHERE a.url = :url")
    fun findDetails(@Param("url") url: String): Optional<AnimeTable>

//    @Query("""
//    select a from AnimeTable a
//    left join a.otherTitles ot
//    where ((:status) is null or a.status = :status)
//    and (:ratingMpa is null or a.ratingMpa = :ratingMpa)
//    and (:season is null or a.season = :season)
//    and (:type is null or a.type = :type)
//    and (:minimalAge is null or a.minimalAge = :minimalAge)
//    and (:year is null or a.year = :year)
//    and (:searchQuery is null or a.title = :searchQuery)
//    and (coalesce(:year, null) is null or :year member of a.year)
//""")
//    fun findAnime(
//        pageable: Pageable,
//        status: String?,
//        searchQuery: String?,
//        ratingMpa: String?,
//        season: String?,
//        minimalAge: Int?,
//        type: String?,
//        year: List<Int>?
//    ): List<AnimeTable>


}