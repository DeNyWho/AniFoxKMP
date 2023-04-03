package com.example.android.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentDetail
import com.example.common.models.common.ContentLight
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.usecase.manga.GetDetailsUseCase
import com.example.common.usecase.manga.GetLinkedUseCase
import com.example.common.usecase.manga.GetSimilarMangaUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailViewModel(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val getLinkedUseCase: GetLinkedUseCase,
    private val getSimilarMangaUseCase: GetSimilarMangaUseCase
): ViewModel() {

    private val _detailManga: MutableState<StateListWrapper<ContentDetail>> =
        mutableStateOf(StateListWrapper.default())
    val detailManga: MutableState<StateListWrapper<ContentDetail>> = _detailManga

    private val _linkedManga: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val linkedManga: MutableState<StateListWrapper<ContentLight>> = _linkedManga

    private val _similarManga: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val similarManga: MutableState<StateListWrapper<ContentLight>> = _similarManga

    fun getDetailManga(contentType: String?, id: String){
        getDetailsUseCase.invoke(contentType, id).onEach {
            _detailManga.value = it
        }.launchIn(viewModelScope)
    }

    fun getSimilarManga(id: String){
        getSimilarMangaUseCase.invoke(id).onEach {
            _similarManga.value = it
        }.launchIn(viewModelScope)
    }

    fun getLinkedManga(id: String){
        getLinkedUseCase.invoke(id).onEach {
            _linkedManga.value = it
        }.launchIn(viewModelScope)
    }

}