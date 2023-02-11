package com.example.common.network

import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class AniFoxApi(
    private val client: HttpClient,
    private var baseUrl: String = "http://192.168.0.43:12200/",
) : KoinComponent {
    suspend fun manga() = client.get("$baseUrl/manga/")
}
