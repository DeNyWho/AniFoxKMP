package com.example.common.di

import com.example.common.usecase.manga.GetDetailsUseCase
import com.example.common.usecase.manga.GetLinkedUseCase
import com.example.common.usecase.manga.GetMangaUseCase
import com.example.common.usecase.anime.GetAnimeUseCase
import com.example.common.usecase.manga.GetSimilarMangaUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetMangaUseCase)
    singleOf(::GetAnimeUseCase)
    singleOf(::GetDetailsUseCase)
    singleOf(::GetLinkedUseCase)
    singleOf(::GetSimilarMangaUseCase)
}