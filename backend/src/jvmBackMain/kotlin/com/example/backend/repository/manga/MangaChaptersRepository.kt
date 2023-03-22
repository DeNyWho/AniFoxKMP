package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaChapters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MangaChaptersRepository: JpaRepository<MangaChapters, UUID>