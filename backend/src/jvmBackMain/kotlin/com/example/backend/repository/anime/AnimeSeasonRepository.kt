package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeSeasonTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeSeasonRepository: JpaRepository<AnimeSeasonTable, String>