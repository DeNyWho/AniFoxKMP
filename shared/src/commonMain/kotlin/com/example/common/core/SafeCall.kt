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
    request: HttpRequestBuilder,
    cookie: Boolean = false
): Resource<T> {
    return try {
        val res: HttpResponse = client.request(request)

        val status = res.status

        when {
            status.isSuccess() -> {
                if (cookie) {
                    val responseCookies = res.headers.getAll("Set-Cookie")
                    Resource.Success(null, responseCookies)
                } else {
                    val body = res.body<T>()
                    Resource.Success(body, listOf())
                }
            }
            status == HttpStatusCode.NotFound -> Resource.Error("Not Found")
//            status == HttpStatusCode.Unauthorized -> Resource.Unauthorized(responseBody.toString())
//            status == HttpStatusCode.Forbidden -> Resource.Forbidden(responseBody.toString())
            else -> Resource.Error(res.body<U>().toString())
        }
    } catch (e: Exception) {
        when (e) {
            is ClientRequestException -> Resource.Error(e.message)
            is ConnectTimeoutException -> Resource.Error(e.message ?: MyError.UNKNOWN_ERROR)
            else -> Resource.Error(MyError.UNKNOWN_ERROR)
        }
    }
}