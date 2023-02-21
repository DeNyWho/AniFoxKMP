package com.example.android.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.presentation.data.StateListWrapper
import com.example.common.usecase.manga.GetDetailsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailViewModel(
    private val getDetailsUseCase: GetDetailsUseCase
): ViewModel() {

    private val _detailManga: MutableState<StateListWrapper<MangaDetail>> =
        mutableStateOf(StateListWrapper.default())
    val detailManga: MutableState<StateListWrapper<MangaDetail>> = _detailManga

    fun getDetailManga(contentType: String?, id: String){
        getDetailsUseCase.invoke(contentType, id).onEach {
            _detailManga.value = it
        }.launchIn(viewModelScope)
    }
}