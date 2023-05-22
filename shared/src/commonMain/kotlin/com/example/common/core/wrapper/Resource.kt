package com.example.common.core.wrapper

sealed class Resource<T>(val data: T? = null, val message: String? = null, val cookie: List<String>? = listOf()) {
    class Success<T>(data: T?, cookie: List<String>?): Resource<T>(data = data, cookie = cookie)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}
