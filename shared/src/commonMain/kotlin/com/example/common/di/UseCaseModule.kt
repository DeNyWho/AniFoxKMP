package com.example.common.di

import com.example.common.usecase.anime.GetAnimeUseCase
import com.example.common.usecase.anime.GetAnimeScreenshotsUseCase
import com.example.common.usecase.anime.GetAnimeMediaUseCase
import com.example.common.usecase.manga.GetDetailsUseCase
import com.example.common.usecase.auth.LoginUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetAnimeUseCase)
    singleOf(::GetDetailsUseCase)
    singleOf(::GetAnimeScreenshotsUseCase)
    singleOf(::GetAnimeMediaUseCase)
    singleOf(::LoginUseCase)
}