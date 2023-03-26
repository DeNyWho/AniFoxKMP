package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeEpisodeTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnimeEpisodeRepository: JpaRepository<AnimeEpisodeTable, String> {

    @Query("Select e from AnimeEpisodeTable e where :link = e.link")
    fun findByEpisodeLink(link: String): Optional<AnimeEpisodeTable>
}