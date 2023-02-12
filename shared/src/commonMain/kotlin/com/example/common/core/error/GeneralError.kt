package com.example.common.core.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralError(

    @SerialName("status")
    val status: Int,

    @SerialName("type")
    val type: String,

    @SerialName("message")
    val message: String,

    @SerialName("error")
    val error: String
)
