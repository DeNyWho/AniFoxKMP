package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaTable
import org.springframework.data.jpa.repository.JpaRepository

interface MangaRepository: JpaRepository<MangaTable, String>