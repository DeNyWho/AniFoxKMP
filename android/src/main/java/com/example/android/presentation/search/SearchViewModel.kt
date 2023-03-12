package com.example.android.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.domain.usecases.GetPagingMangaUseCase
import com.example.common.models.mangaResponse.light.MangaLight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel (
    private val getPagingMangaUseCase: GetPagingMangaUseCase
): ViewModel() {

    private val _searchedManga = MutableStateFlow<PagingData<MangaLight>>(PagingData.empty())
    val searchedManga = _searchedManga

    fun search(query: String) {
        viewModelScope.launch {
            getPagingMangaUseCase.invoke(searchQuery = query).cachedIn(viewModelScope).collect {
                _searchedManga.value = it
            }
        }
    }

}