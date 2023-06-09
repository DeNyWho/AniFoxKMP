package com.example.common.di

import com.example.common.usecase.anime.GetAnimeUseCase
import com.example.common.usecase.anime.GetAnimeScreenshotsUseCase
import com.example.common.usecase.anime.GetAnimeMediaUseCase
import com.example.common.usecase.anime.GetAnimeSimilarUseCase
import com.example.common.usecase.anime.GetAnimeRelatedUseCase
import com.example.common.usecase.manga.GetDetailsUseCase
import com.example.common.usecase.manga.GetMangaUseCase
import com.example.common.usecase.auth.LoginUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetAnimeUseCase)
    singleOf(::GetDetailsUseCase)
    singleOf(::GetAnimeScreenshotsUseCase)
    singleOf(::GetAnimeRelatedUseCase)
    singleOf(::GetAnimeMediaUseCase)
    singleOf(::GetAnimeSimilarUseCase)
    singleOf(::GetMangaUseCase)
    singleOf(::LoginUseCase)
}