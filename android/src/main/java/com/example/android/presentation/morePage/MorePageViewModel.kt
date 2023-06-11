package com.example.android.presentation.morePage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.domain.common.StateListWrapper
//import com.example.android.domain.usecases.GetPagingMangaUseCase
import com.example.common.models.common.ContentLight
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.usecase.manga.GetMangaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MorePageViewModel (
    private val getMangaUseCase: GetMangaUseCase
): ViewModel(){

    private val _searchedManga: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val searchedManga = _searchedManga

    fun search(order: String? = null, status: String? = null, genres: List<String>? = null) {
        getMangaUseCase.invoke(order = order, status = status, genres = genres).onEach {
            _searchedManga.value = it
        }
    }

}