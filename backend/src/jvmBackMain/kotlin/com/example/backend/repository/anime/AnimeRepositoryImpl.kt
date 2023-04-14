package com.example.backend.repository.anime

import com.example.backend.jpa.anime.AnimeGenreTable
import com.example.backend.jpa.anime.AnimeStudiosTable
import com.example.backend.jpa.anime.AnimeTranslationTable
import com.example.backend.models.ServiceResponse
import com.example.backend.models.animeResponse.detail.AnimeDetail
import com.example.backend.models.animeResponse.light.AnimeLight
import org.springframework.stereotype.Repository

@Repository
interface AnimeRepositoryImpl {

    fun addDataToDB()
    fun getAnime(
        pageNum: Int,
        pageSize: Int,
        order: String?,
        genres: List<String>?,
        status: String?,
        searchQuery: String?,
        ratingMpa: String?,
        season: String?,
        minimalAge: Int?,
        type: String?,
        year: List<Int>?,
        translations: List<String>?
    ): ServiceResponse<AnimeLight>

    fun getAnimeGenres(): ServiceResponse<AnimeGenreTable>
    fun getAnimeStudios(): ServiceResponse<AnimeStudiosTable>
    fun getAnimeTranslations(): ServiceResponse<AnimeTranslationTable>
    fun getAnimeYears(): ServiceResponse<String>
    fun getAnimeById(id: String): ServiceResponse<AnimeDetail>
}