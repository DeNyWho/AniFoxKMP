package com.example.common.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.common.network.AniFoxApi

internal val networkModule = module {
    singleOf(::AniFoxApi)
}