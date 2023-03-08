package com.example.common.di

import com.example.common.data.repository.MangaRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val networkModule = module {
    singleOf(::MangaRepository)
}