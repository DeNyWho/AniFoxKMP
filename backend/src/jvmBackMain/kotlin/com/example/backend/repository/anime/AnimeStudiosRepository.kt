package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeStudiosTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnimeStudiosRepository: JpaRepository<AnimeStudiosTable, String> {

    @Query("Select g from AnimeStudiosTable g where :studio = g.studio")
    fun findByStudio(studio: String): Optional<AnimeStudiosTable>
}