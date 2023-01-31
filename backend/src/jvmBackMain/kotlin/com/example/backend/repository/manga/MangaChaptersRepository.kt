package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaChapters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MangaChaptersRepository: JpaRepository<MangaChapters, String>