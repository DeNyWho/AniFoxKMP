package com.example.android.di

import com.example.android.presentation.home.HomeViewModel
import com.example.android.presentation.signIn.SignInViewModel
import com.example.android.presentation.signUp.SignUpViewModel
import com.example.android.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    viewModel { SplashViewModel() }
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel {
        HomeViewModel(get())
    }
}