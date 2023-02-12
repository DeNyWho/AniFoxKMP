package com.example.android.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.compose.RandomMangaState
import com.example.common.usecase.manga.GetRandomMangaUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getRandomMangaUseCase: GetRandomMangaUseCase
): ViewModel() {

    private val _randomManga: MutableState<RandomMangaState> =
        mutableStateOf(RandomMangaState())
    val randomManga: MutableState<RandomMangaState> = _randomManga

    fun getRandomManga(){
        getRandomMangaUseCase.invoke().onEach {
            _randomManga.value = it
        }.launchIn(viewModelScope)
    }
}