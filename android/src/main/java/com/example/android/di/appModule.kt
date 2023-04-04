package com.example.android.di

import coil.ImageLoader
import com.example.android.composable.ImageLoaderSingleton
import com.example.android.domain.usecases.GetPagingMangaUseCase
import com.example.android.presentation.anime.AnimeViewModel
import com.example.android.presentation.detail.DetailViewModel
import com.example.android.presentation.home.HomeViewModel
import com.example.android.presentation.manga.MangaViewModel
import com.example.android.presentation.morePage.MorePageViewModel
import com.example.android.presentation.search.SearchViewModel
import com.example.android.presentation.signIn.SignInViewModel
import com.example.android.presentation.signUp.SignUpViewModel
import com.example.android.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val appModule = module {
    viewModel { SplashViewModel() }
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { MangaViewModel(get()) }
    viewModel { AnimeViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { DetailViewModel( get(), get(), get()) }
    viewModel { MorePageViewModel( get()) }
    viewModel { SearchViewModel( get()) }
    singleOf(::GetPagingMangaUseCase)
    single {
        ImageLoader.Builder(androidContext())
            .build()
    }
    single { ImageLoaderSingleton() }
}