package com.example.android.presentation.anime

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.example.common.usecase.anime.GetAnimeUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val getAnimeUseCase: GetAnimeUseCase
): ViewModel() {

    private val _onGoingAnime: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onGoingAnime: MutableState<StateListWrapper<ContentLight>> = _onGoingAnime

    private val _onWinterAnime: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onWinterAnime: MutableState<StateListWrapper<ContentLight>> = _onWinterAnime

    private val _onSpringAnime: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onSpringAnime: MutableState<StateListWrapper<ContentLight>> = _onSpringAnime

    private val _onSummerAnime: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onSummerAnime: MutableState<StateListWrapper<ContentLight>> = _onSummerAnime

    private val _onFallAnime: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onFallAnime: MutableState<StateListWrapper<ContentLight>> = _onFallAnime

    fun getOnGoingAnime() {
        getAnimeUseCase.invoke(status = "ongoing").onEach {
            _onGoingAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getOnWinterAnime(year: Int? = null) {
        getAnimeUseCase.invoke(season = "Winter", year = year).onEach {
            _onWinterAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getOnSpringAnime(year: Int? = null) {
        getAnimeUseCase.invoke(season = "Spring", year = year).onEach {
            _onSpringAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getOnSummerAnime(year: Int? = null) {
        getAnimeUseCase.invoke(season = "Summer", year = year).onEach {
            _onSummerAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getOnFallAnime(year: Int? = null) {
        getAnimeUseCase.invoke(season = "Fall", year = year).onEach {
            _onFallAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getAnimeSequentially(
        winterYear: Int?,
        springYear: Int?,
        summerYear: Int?,
        fallYear: Int?
    ) {
        viewModelScope.launch {
            val onGoingDeferred = async { getOnGoingAnime() }
            onGoingDeferred.await()

            val winterDeferred = async { getOnWinterAnime(year = winterYear) }
            winterDeferred.await()

            val springDeferred = async { getOnSpringAnime(year = springYear) }
            springDeferred.await()

            val summerDeferred = async { getOnSummerAnime(year = summerYear) }
            summerDeferred.await()

            val fallDeferred = async { getOnFallAnime(year = fallYear) }
            fallDeferred.await()

        }
    }
}