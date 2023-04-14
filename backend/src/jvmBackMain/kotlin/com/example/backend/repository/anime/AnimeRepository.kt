package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeTable
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnimeRepository : JpaRepository<AnimeTable, String> {


    fun findByTitle(title: String): Optional<AnimeTable>

    @Query("Select distinct a.year from AnimeTable a order by a.year desc")
    fun findDistinctByYear(): List<String>

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