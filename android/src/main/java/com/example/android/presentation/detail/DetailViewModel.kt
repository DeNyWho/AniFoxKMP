package com.example.android.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper

class DetailViewModel(
): ViewModel() {

    private val _detailManga: MutableState<StateListWrapper<MangaLight>> =
        mutableStateOf(StateListWrapper.default())
    val detailManga: MutableState<StateListWrapper<MangaLight>> = _detailManga
}