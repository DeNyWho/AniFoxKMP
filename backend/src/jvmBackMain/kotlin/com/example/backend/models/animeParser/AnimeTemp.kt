@file:UseSerializers(LocalDateTimeSerializer::class)
package com.example.backend.models.animeParser

import com.example.backend.util.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDateTime

@Serializable
data class AnimeTemp(
    @SerialName("id")
    val id: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("title_orig")
    val title: String = "",
    @SerialName("link")
    val link: String = "",
    @SerialName("translation")
    val translation: Translation = Translation(),
    @SerialName("shikimori_id")
    val shikimoriId: String = "",
    @SerialName("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @SerialName("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @SerialName("material_data")
    val materialData: MaterialData = MaterialData(),
    @SerialName("screenshots")
    val screenshots: List<String> = listOf()
)