package com.example.android.presentation.manga

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.constraintlayout.solver.state.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateListWrapper
import com.example.common.domain.common.StateWrapper
import com.example.common.models.common.ContentLight
import com.example.common.models.common.ContentLightWithPaging
import com.example.common.models.common.Pagination
import com.example.common.usecase.manga.GetMangaUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MangaViewModel(
    private val getMangaUseCase: GetMangaUseCase,
): ViewModel() {
    private val _contentState: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val contentState: MutableState<StateListWrapper<ContentLight>> = _contentState

    private var isWaiting: Boolean = false

    private var currentPage: Int = 0

    private var search: String? = null

    fun getNewSearch(query: String) {
        currentPage = 0
        search = query
        getMangaUseCase.invoke(pageNum = currentPage, searchQuery = search)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _contentState.value = _contentState.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaiting = false
                }
                _contentState.value = newState
            }.launchIn(viewModelScope)
    }

    fun getNextContentPart() {
        currentPage = nextContentPage()
        isWaiting = true
        getMangaUseCase.invoke(pageNum = currentPage, searchQuery = search)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _contentState.value = _contentState.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaiting = false
                }

                _contentState.value = _contentState.value.copy(_contentState.value.data.plus(newState.data))
            }.launchIn(viewModelScope)
    }

    private fun nextContentPage(): Int {
        return currentPage + 1
    }
}