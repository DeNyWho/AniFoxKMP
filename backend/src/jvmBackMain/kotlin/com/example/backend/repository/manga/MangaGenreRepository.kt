package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaGenre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MangaGenreRepository : JpaRepository<MangaGenre, String>