package com.example.common.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.common.usecase.manga.GetRandomMangaUseCase

internal val useCaseModule = module {
    singleOf(::GetRandomMangaUseCase)
}