package com.example.common.core

import com.example.common.core.error.GeneralError
import com.example.common.core.exception.MyError
import com.example.common.core.wrapper.Resource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend inline fun <reified T: Any, reified U: Any> safeApiCall(
        client: HttpClient,
        request: HttpRequestBuilder
    ): Resource<T> {
    return try {
        val res: HttpResponse = client.request(request)

        if (res.status.isSuccess()) {
            val body = res.body<T>()
            Resource.Success(body)
        } else {
            println(res.body<U>())
            when (val error = res.body<U>()) {
                is GeneralError -> Resource.Error(error.message)
                else -> Resource.Error(message = res.body<U>().toString())
            }

        }
    } catch (e: Exception) {
        println(e.message)
        when (e) {
            is ClientRequestException -> Resource.Error(e.message)
            is ConnectTimeoutException -> Resource.Error(e.message ?: MyError.UNKNOWN_ERROR)
            else -> Resource.Error(MyError.UNKNOWN_ERROR)
        }
    }
}