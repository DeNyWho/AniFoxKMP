package com.example.common.di

import com.example.common.usecase.anime.GetAnimeMediaUseCase
import com.example.common.usecase.anime.GetAnimeScreenshotsUseCase
import com.example.common.usecase.anime.GetAnimeUseCase
import com.example.common.usecase.content.GetDetailsUseCase
import com.example.common.usecase.content.GetRelatedUseCase
import com.example.common.usecase.content.GetSimilarUseCase
import com.example.common.usecase.manga.GetMangaChaptersInfoUseCase
import com.example.common.usecase.manga.GetMangaChaptersUseCase
import com.example.common.usecase.manga.GetMangaUseCase
import com.example.common.usecase.user.GetFavoriteListUseCase
import com.example.common.usecase.user.SetFavoriteListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetAnimeUseCase)
    singleOf(::GetDetailsUseCase)
    singleOf(::GetAnimeScreenshotsUseCase)
    singleOf(::GetMangaChaptersUseCase)
    singleOf(::GetMangaChaptersInfoUseCase)
    singleOf(::GetFavoriteListUseCase)
    singleOf(::SetFavoriteListUseCase)
    singleOf(::GetRelatedUseCase)
    singleOf(::GetAnimeMediaUseCase)
    singleOf(::GetSimilarUseCase)
    singleOf(::GetMangaUseCase)
}