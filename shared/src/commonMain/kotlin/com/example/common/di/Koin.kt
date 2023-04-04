package com.example.common.di

//import com.example.common.data.paging.MangaPagingSource
import com.example.common.data.repository.AnimeRepository
import com.example.common.data.repository.MangaRepository
import com.example.common.repository.platformModule
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(enableNetworkLogs = true),
            platformModule(),
            networkModule,
            useCaseModule
        )
    }

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }

    single { MangaRepository(get()) }
    single { AnimeRepository(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }


fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpCache)
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
    install(HttpTimeout){
        requestTimeoutMillis = 300000
    }
}
