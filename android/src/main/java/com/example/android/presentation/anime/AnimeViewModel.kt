package com.example.android.presentation.anime

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.common.ContentLight
import com.example.common.usecase.anime.GetAnimeUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AnimeViewModel(
    private val getAnimeUseCase: GetAnimeUseCase,
): ViewModel() {

    private val _onGoingAnime: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onGoingAnime: MutableState<StateListWrapper<ContentLight>> = _onGoingAnime


    fun getOnGoingAnime() {
        getAnimeUseCase.invoke(status = "ongoing").onEach {
            _onGoingAnime.value = it
        }.launchIn(viewModelScope)
    }
}