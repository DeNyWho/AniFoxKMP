package com.example.common.core

import com.example.common.core.exception.MyError
import com.example.common.core.wrapper.Resource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend inline fun <reified T : Any, reified U : Any> safeApiCall(
    client: HttpClient,
    request: HttpRequestBuilder
): Resource<T> {
    return try {
        val res: HttpResponse = client.request(request)
        println("WWW ZZ S = ${res.status}")
        println("WWW ZZ S = ${res.body<T>()}")

        when (res.status) {
            HttpStatusCode.Created -> {
                Resource.Success(data = res.body<T>(), message = "Created")
            }
            HttpStatusCode.OK -> {
                Resource.Success(data = res.body<T>(), message = "OK")
            }
            HttpStatusCode.Unauthorized -> Resource.Error(MyError.WRONG_CREDENTIALS)
            HttpStatusCode.NotFound -> Resource.Error("Not Found")
            else -> Resource.Error(res.body<U>().toString())
        }
    } catch (e: Exception) {
        when (e) {
            is ClientRequestException -> Resource.Error(e.message)
            is ConnectTimeoutException -> Resource.Error(e.message ?: MyError.UNKNOWN_ERROR)
            else -> {
                println(e.message)
                Resource.Error(MyError.UNKNOWN_ERROR)
            }
        }
    }
}