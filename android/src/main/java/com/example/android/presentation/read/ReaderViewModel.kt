package com.example.android.presentation.read

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.mangaResponse.chapters.ChaptersLight
import com.example.common.usecase.manga.GetMangaChaptersInfoUseCase
import com.example.common.usecase.manga.GetMangaChaptersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReaderViewModel (
    private val mangaChaptersUseCase: GetMangaChaptersUseCase,
    private val mangaChaptersInfoUseCase: GetMangaChaptersInfoUseCase
): ViewModel() {
    private val _contentState: MutableState<StateListWrapper<ChaptersLight>> =
        mutableStateOf(StateListWrapper.default())
    val contentState: MutableState<StateListWrapper<ChaptersLight>> = _contentState

    private val _readerState: MutableState<StateListWrapper<String>> =
        mutableStateOf(StateListWrapper.default())
    val readerState: MutableState<StateListWrapper<String>> = _readerState

    private var isWaiting: Boolean = false

    private var currentPage: Int = 0

    fun getChapter(mangaId: String, chapterId: String) {
        mangaChaptersInfoUseCase.invoke(mangaId, chapterId).onEach {
            _readerState.value = it
        }.launchIn(viewModelScope)
    }

    fun getNextContentPart(mangaId: String) {
        isWaiting = true
        mangaChaptersUseCase.invoke(pageNum = currentPage, mangaId = mangaId)
            .onEach { newState: StateListWrapper<ChaptersLight> ->
                if (newState.isLoading) {
                    _contentState.value = _contentState.value.copy(isLoading = true)
                    return@onEach
                } else {
                    isWaiting = false
                }
                currentPage = nextContentPage()

                _contentState.value = _contentState.value.copy(data = _contentState.value.data.plus(newState.data), isLoading = false)
            }.launchIn(viewModelScope)
    }

    private fun nextContentPage(): Int {
        return currentPage + 1
    }
}