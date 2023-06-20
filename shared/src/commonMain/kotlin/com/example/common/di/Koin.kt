package com.example.common.di

//import com.example.common.data.paging.MangaPagingSource
import com.example.common.repository.platformModule
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.apache5.*
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

fun initKoin(enableNetworkLogs: Boolean = true, context: Any,  appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(enableNetworkLogs = true, context),
            platformModule(),
            networkModule,
            useCaseModule
        )
    }

fun commonModule(enableNetworkLogs: Boolean, context: Any) = module {
    single { createJson() }
    single { createHttpClient(get(), context, get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = false
}


fun createHttpClient(httpClientEngine: HttpClientEngine, context: Any, json: Json, enableNetworkLogs: Boolean) = HttpClient(Apache5) {
    engine {
        sslContext = getSSLContext(context)
    }
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpCache)
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
    }
    install(HttpTimeout){
        requestTimeoutMillis = 300000
    }
}
