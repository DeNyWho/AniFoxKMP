package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeTranslationTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeTranslationRepository: JpaRepository<AnimeTranslationTable, Int>