package com.example.android.presentation.morePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.domain.usecases.GetPagingMangaUseCase
import com.example.common.models.common.ContentLight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MorePageViewModel (
    private val getPagingMangaUseCase: GetPagingMangaUseCase
): ViewModel(){

    private val _searchedManga = MutableStateFlow<PagingData<ContentLight>>(PagingData.empty())
    val searchedManga = _searchedManga

    fun search(order: String? = null, status: String? = null, genres: List<String>? = null) {
        viewModelScope.launch {
            getPagingMangaUseCase.invoke(order = order, status = status, genres = genres).cachedIn(viewModelScope).collect {
                _searchedManga.value = it
            }
        }
    }
}