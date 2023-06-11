package com.example.android.presentation.myList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.usecases.ReadTokenUseCase
import com.example.common.domain.common.StateListWrapper
import com.example.common.domain.common.StateWrapper
import com.example.common.models.common.ContentLight
import com.example.common.usecase.anime.GetAnimeUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MyListViewModel(
    private val getAnimeUseCase: GetAnimeUseCase,
    private val readTokenUseCase: ReadTokenUseCase
): ViewModel() {

    private val _onWatch: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onWatch: MutableState<StateListWrapper<ContentLight>> = _onWatch

    private val _inPlan: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val inPlan: MutableState<StateListWrapper<ContentLight>> = _inPlan

    private val _onWatched: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onWatched: MutableState<StateListWrapper<ContentLight>> = _onWatched

    private val _onPostponed: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val onPostponed: MutableState<StateListWrapper<ContentLight>> = _onPostponed

    private var currentPageOnWatch = 0
    private var currentPageOnWatched = 0
    private var currentPageOnPostponed = 0
    private var currentPageInPlan = 0

    private var isWaitingOnWatch: Boolean = false
    private var isWaitingOnWatched: Boolean = false
    private var isWaitingOnPostponed: Boolean = false
    private var isWaitingInPlan: Boolean = false


    private val _token: MutableState<StateWrapper<String>> =
        mutableStateOf(StateWrapper.loading())
    val token: MutableState<StateWrapper<String>> = _token

    init {
        fetchToken()
    }

    private fun fetchToken() {
        viewModelScope.launch {
            readTokenUseCase.invoke().collect { token ->
                _token.value = token
                myListReq()
            }
        }
    }

    private fun myListReq () {
        viewModelScope.launch {
            if(token.value.data != null && token.value.data?.length!! > 2) {
                val onWatchDeferred = async { getNextContentPartOnWatch() }
                onWatchDeferred.await()

                val inPlanDeferred = async { getNextContentPartInPlan() }
                inPlanDeferred.await()

                val onWatchedDeferred = async { getNextContentPartOnWatched() }
                onWatchedDeferred.await()

                val postPonedDeferred = async { getNextContentPartOnPostponed() }
                postPonedDeferred.await()
            }
        }
    }

    fun getNextContentPartOnWatch() {
        isWaitingOnWatch = true
        getAnimeUseCase.invoke(pageNum = currentPageOnWatch)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onWatch.value = _onWatch.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnWatch = false
                }

                _onWatch.value = _onWatch.value.copy(_onWatch.value.data.plus(newState.data))

                currentPageOnWatch++
            }.launchIn(viewModelScope)
    }

    fun getNextContentPartOnWatched() {
        isWaitingOnWatched = true
        getAnimeUseCase.invoke(pageNum = currentPageOnWatched)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onWatched.value = _onWatched.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnWatched = false
                }

                _onWatched.value = _onWatched.value.copy(_onWatched.value.data.plus(newState.data))
            }.launchIn(viewModelScope)
        currentPageOnWatched++
    }

    fun getNextContentPartOnPostponed() {
        isWaitingOnPostponed = true
        getAnimeUseCase.invoke(pageNum = currentPageOnPostponed)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onPostponed.value = _onPostponed.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnPostponed = false
                }

                _onPostponed.value = _onPostponed.value.copy(_onPostponed.value.data.plus(newState.data))
            }.launchIn(viewModelScope)
        currentPageOnPostponed++
    }

    fun getNextContentPartInPlan() {
        isWaitingInPlan = true
        getAnimeUseCase.invoke(pageNum = currentPageInPlan)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _inPlan.value = _inPlan.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingInPlan = false
                }

                _inPlan.value = _inPlan.value.copy(_inPlan.value.data.plus(newState.data))
            }.launchIn(viewModelScope)
        currentPageInPlan++
    }

}