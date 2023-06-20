@file:UseSerializers(LocalDateSerializer::class)
package com.example.common.models.mangaResponse.chapters

import com.example.common.util.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class ChaptersLight(
    @SerialName("title")
    val title: String = "",
    @SerialName("urlCode")
    val urlCode: Int = 0,
    @SerialName("date")
    val date: String = "",
    @SerialName("id")
    val id: String
)