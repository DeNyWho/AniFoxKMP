package com.example.android.presentation.home.state

import com.example.common.models.AnimeLight

data class AnimePopularState(
    val data: List<AnimeLight> = listOf(),
    val isLoading: Boolean = false
)