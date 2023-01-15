package com.example.android.di

import com.example.android.presentation.home.HomeViewModel
import com.example.android.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel() }
}