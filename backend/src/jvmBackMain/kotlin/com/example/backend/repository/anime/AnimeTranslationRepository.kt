package com.example.backend.repository.anime

import com.example.backend.jpa.anime.TranslationTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeTranslationRepository: JpaRepository<TranslationTable, Int>