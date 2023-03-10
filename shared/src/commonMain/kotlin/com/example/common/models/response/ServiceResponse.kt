package com.example.common.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ServiceResponse<T>(
    var data: List<T>? = null,
    var message: String = ""
)