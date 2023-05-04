package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeMediaTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimeMediaRepository : JpaRepository<AnimeMediaTable, String>