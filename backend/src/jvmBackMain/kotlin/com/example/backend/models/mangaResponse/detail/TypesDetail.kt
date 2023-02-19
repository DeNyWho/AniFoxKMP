package com.example.backend.models.mangaResponse.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypesDetail(
    @SerialName("id")
    val id: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("year")
    val year: Int? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("limitation")
    val limitation: String? = null
)