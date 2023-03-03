package com.example.android.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper
import com.example.common.usecase.manga.GetRandomMangaUseCase
import com.example.common.usecase.manga.GetMangaByGenreUseCase
import com.example.common.usecase.manga.GetMangaUseCase
import com.example.common.util.Constants
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getMangaUseCase: GetMangaUseCase,
): ViewModel() {

    private val _randomManga: MutableState<StateListWrapper<MangaLight>> =
        mutableStateOf(StateListWrapper.default())
    val randomManga: MutableState<StateListWrapper<MangaLight>> = _randomManga

    private val _romanceManga: MutableState<StateListWrapper<MangaLight>> =
        mutableStateOf(StateListWrapper.default())
    val romanceManga: MutableState<StateListWrapper<MangaLight>> = _romanceManga

    fun getRandomManga() {
        getMangaUseCase.invoke(order = "random").onEach {
            _randomManga.value = it
        }.launchIn(viewModelScope)
    }

    fun getRomanceManga() {
        getMangaUseCase.invoke(
            genres = listOf(
                Constants.romance,
                Constants.dramma,
                Constants.sedze
            )
        ).onEach {
            _romanceManga.value = it
        }.launchIn(viewModelScope)
    }
}