package com.example.backend.service.manga

import com.example.backend.repository.manga.MangaRepository
import com.example.backend.repository.manga.MangaRepositoryImpl
import org.springframework.stereotype.Service

@Service
class MangaService: MangaRepositoryImpl {
    override fun addDataToDB() {
        TODO("Not yet implemented")
    }

}