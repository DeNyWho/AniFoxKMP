package com.example.common.di

import com.example.common.usecase.manga.GetRandomMangaUseCase
import com.example.common.usecase.manga.GetRomanceMangaUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetRandomMangaUseCase)
    singleOf(::GetRomanceMangaUseCase)
}