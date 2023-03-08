package com.example.android.presentation.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.util.Constants.SEARCH_QUERY_TOO_SHORT
import com.example.common.domain.common.SearchEvent
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.domain.common.StateListWrapper
import com.example.common.usecase.manga.GetMangaUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel (
    private val getMangaUseCase: GetMangaUseCase
): ViewModel() {

    private val _contentSearch: MutableState<StateListWrapper<MangaLight>> =
        mutableStateOf(StateListWrapper.default())
    val contentSearch: MutableState<StateListWrapper<MangaLight>> = _contentSearch

    private var delayJob: Job = Job()
    private var searchJob: Job = Job()
    private var currentPage: Int = 0

    private fun searchContentByQuery(query: String) {
        if (searchJob.isActive) {
            searchJob.cancel()
        }
        println("WAF WAF WAF = $query")
        if (query.length >= 0 || query.isEmpty()) {
            if (searchJob.isActive) {
                searchJob.cancel()
            }
            searchJob = getMangaUseCase.invoke(searchQuery = query, pageNum = 0, pageSize = 24).onEach { res ->
                println("WAF WAFSADDS = $res")
                _contentSearch.value = res
                if (res.error.peekContent() != null) currentPage = 2
            }.launchIn(viewModelScope)
        } else {
            currentPage = 1
            _contentSearch.value = StateListWrapper.error(SEARCH_QUERY_TOO_SHORT)
        }
    }

    private fun nextContentPageByQuery(query: String) {
        if (contentSearch.value.data.isEmpty()) {
            return
        }
        if (query.length >= 0 && !searchJob.isActive) {
            getMangaUseCase.invoke(
                searchQuery = query,
                pageSize = 24,
                pageNum = currentPage
            ).onEach { res ->
                if (res.isLoading) {
                    _contentSearch.value = _contentSearch.value.copy(isLoading = true)
                    return@onEach
                }
                if (res.error.peekContent() == null) {
                    currentPage++
                    _contentSearch.value = _contentSearch.value.copy(res.data, false, res.error)
                } else {
                    _contentSearch.value = _contentSearch.value.copy(error = res.error)
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onSearchEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchFirstPage -> {
                delayJob.cancel()
                delayJob = viewModelScope.launch {
                    delay(1_500)
                    searchContentByQuery(event.query)
                }
            }
            is SearchEvent.SearchNextPage -> {
                nextContentPageByQuery(event.query)
            }
        }
    }


}