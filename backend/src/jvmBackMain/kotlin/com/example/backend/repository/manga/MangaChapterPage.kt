package com.example.backend.repository.manga

import com.example.backend.jpa.manga.MangaChaptersPage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
@Repository
interface MangaChapterPageRepository: JpaRepository<MangaChaptersPage, String>