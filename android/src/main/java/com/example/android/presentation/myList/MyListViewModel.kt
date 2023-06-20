package com.example.android.presentation.myList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.usecases.ReadTokenUseCase
import com.example.common.core.enum.ContentType
import com.example.common.core.enum.StatusListType
import com.example.common.domain.common.StateListWrapper
import com.example.common.domain.common.StateWrapper
import com.example.common.models.common.ContentLight
import com.example.common.usecase.user.GetFavoriteListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MyListViewModel(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val readTokenUseCase: ReadTokenUseCase
): ViewModel() {

    private val _onWatch: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.loading())
    val onWatch: MutableState<StateListWrapper<ContentLight>> = _onWatch

    private val _inPlan: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.loading())
    val inPlan: MutableState<StateListWrapper<ContentLight>> = _inPlan

    private val _onWatched: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.loading())
    val onWatched: MutableState<StateListWrapper<ContentLight>> = _onWatched

    private val _onPostponed: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.loading())
    val onPostponed: MutableState<StateListWrapper<ContentLight>> = _onPostponed

    private var currentPageOnWatch = 0
    private var currentPageOnWatched = 0
    private var currentPageOnPostponed = 0
    private var currentPageInPlan = 0

    private var isWaitingOnWatch: Boolean = false
    private var isWaitingOnWatched: Boolean = false
    private var isWaitingOnPostponed: Boolean = false
    private var isWaitingInPlan: Boolean = false

    private var contentType: String = ContentType.Anime.name


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
        getFavoriteListUseCase.invoke(pageNum = currentPageOnWatch, statusListType = StatusListType.Watching, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onWatch.value = _onWatch.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnWatch = false
                }

                _onWatch.value = _onWatch.value.copy(data = _onWatch.value.data.plus(newState.data), isLoading = false)

                currentPageOnWatch++
            }.launchIn(viewModelScope)
    }

    fun getNextContentPartOnWatched() {
        isWaitingOnWatched = true
        getFavoriteListUseCase.invoke(pageNum = currentPageOnWatched, statusListType = StatusListType.Watched, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onWatched.value = _onWatched.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnWatched = false
                }
                _onWatched.value = _onWatched.value.copy(data = _onWatched.value.data.plus(newState.data), isLoading = false)
            }.launchIn(viewModelScope)
        currentPageOnWatched++
    }

    fun getNextContentPartOnPostponed() {
        isWaitingOnPostponed = true
        getFavoriteListUseCase.invoke(pageNum = currentPageOnPostponed, statusListType = StatusListType.Postponed, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onPostponed.value = _onPostponed.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnPostponed = false
                }

                _onPostponed.value = _onPostponed.value.copy(data = _onPostponed.value.data.plus(newState.data), isLoading = false)
            }.launchIn(viewModelScope)
        currentPageOnPostponed++
    }

    fun getNextContentPartInPlan() {
        isWaitingInPlan = true
        getFavoriteListUseCase.invoke(pageNum = currentPageInPlan, statusListType = StatusListType.InPlan, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _inPlan.value = _inPlan.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingInPlan = false
                }

                _inPlan.value = _inPlan.value.copy(data = _inPlan.value.data.plus(newState.data), isLoading = false)
            }.launchIn(viewModelScope)
        currentPageInPlan++
    }

    fun refreshDataInPlan() {
        isWaitingInPlan = true
        currentPageInPlan = 0
        getFavoriteListUseCase.invoke(pageNum = currentPageInPlan, statusListType = StatusListType.InPlan, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _inPlan.value = _inPlan.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingInPlan = false
                }
                _inPlan.value = _inPlan.value.copy(data = newState.data, isLoading = false)
            }.launchIn(viewModelScope)
        currentPageInPlan++
    }

    fun refreshDataWatch() {
        isWaitingOnWatch = true
        currentPageOnWatch = 0
        println("TOKE = ${token.value.data}")
        getFavoriteListUseCase.invoke(pageNum = currentPageOnWatch, statusListType = StatusListType.Watching, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onWatch.value = _onWatch.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnWatch = false
                }
                _onWatch.value = _onWatch.value.copy(data = newState.data, isLoading = false)
            }.launchIn(viewModelScope)
        currentPageOnWatch++
    }

    fun refreshDataWatched() {
        isWaitingOnWatched = true
        currentPageOnWatched = 0
        getFavoriteListUseCase.invoke(pageNum = currentPageOnWatched, statusListType = StatusListType.Watched, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onWatched.value = _onWatched.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnWatched = false
                }
                _onWatched.value = _onWatched.value.copy(data = newState.data, isLoading = false)
            }.launchIn(viewModelScope)
        currentPageOnWatched++
    }

    fun refreshDataPostponed() {
        isWaitingOnPostponed = true
        currentPageOnPostponed = 0
        getFavoriteListUseCase.invoke(pageNum = currentPageOnPostponed, statusListType = StatusListType.Postponed, contentType = contentType, token = token.value.data!!)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _onPostponed.value = _onPostponed.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaitingOnPostponed = false
                }
                _onPostponed.value = _onPostponed.value.copy(data = newState.data, isLoading = false)
            }.launchIn(viewModelScope)
        currentPageOnPostponed++
    }

    fun getContentType(): String {
        return contentType
    }

    fun setContentType(type: String) {
        _onWatch.value = StateListWrapper.loading()
        _inPlan.value = StateListWrapper.loading()
        _onPostponed.value = StateListWrapper.loading()
        _onWatched.value = StateListWrapper.loading()
        contentType = type
        currentPageOnWatched = 0
        currentPageOnWatch = 0
        currentPageInPlan = 0
        currentPageOnPostponed = 0
        myListReq()
    }

}