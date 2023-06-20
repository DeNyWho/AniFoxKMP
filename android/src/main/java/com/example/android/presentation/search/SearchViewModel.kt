package com.example.android.presentation.search

//import com.example.android.domain.usecases.GetPagingMangaUseCase
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.example.common.usecase.anime.GetAnimeUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel (
    private val getAnimeUseCase: GetAnimeUseCase,
): ViewModel() {

    private val _contentState: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val contentState: MutableState<StateListWrapper<ContentLight>> = _contentState

    private var search: String? = null
    private var currentPage: Int = 0
    private var isWaiting: Boolean = false


    fun getNewSearch(query: String) {
        currentPage = 0
        search = query
        getAnimeUseCase.invoke(pageNum = currentPage, searchQuery = search)
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
        getAnimeUseCase.invoke(pageNum = currentPage, searchQuery = search)
            .onEach { newState: StateListWrapper<ContentLight> ->
                if (newState.isLoading) {
                    _contentState.value = _contentState.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaiting = false
                }

                _contentState.value = _contentState.value.copy(data = _contentState.value.data.plus(newState.data), isLoading = false)
            }.launchIn(viewModelScope)
    }

    private fun nextContentPage(): Int {
        return currentPage + 1
    }

}