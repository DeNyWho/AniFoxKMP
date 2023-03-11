package com.example.android.presentation.search

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.util.Constants.SEARCH_QUERY_TOO_SHORT
import com.example.common.data.paging.MangaPagingSource
import com.example.common.domain.common.SearchEvent
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.domain.common.StateListWrapper
import com.example.common.usecase.manga.GetMangaUseCase
import com.example.common.usecase.manga.GetPagingMangaUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel (
    private val getPagingMangaUseCase: GetPagingMangaUseCase
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedManga = MutableStateFlow<PagingData<MangaLight>>(PagingData.empty())
    val searchedManga = _searchedManga

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun search(query: String) {
        viewModelScope.launch {
            getPagingMangaUseCase.invoke(searchQuery = query).cachedIn(viewModelScope).collect {
                _searchedManga.value = it
            }
        }
    }

}