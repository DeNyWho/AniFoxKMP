package com.example.common.compose

import com.example.common.core.wrapper.Event
import com.example.common.models.mangaResponse.light.MangaLight

data class RandomMangaState(
    val data: List<MangaLight> = listOf(),
    val isLoading: Boolean = false,
    val error: Event<String?> = Event(null)
)