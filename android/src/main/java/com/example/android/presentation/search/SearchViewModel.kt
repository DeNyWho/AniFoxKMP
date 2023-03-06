package com.example.android.presentation.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper
import com.example.common.usecase.manga.GetMangaUseCase
import kotlinx.coroutines.Job

class SearchViewModel (
    private val getMangaUseCase: GetMangaUseCase
): ViewModel() {

    private val _contentSearch: MutableState<StateListWrapper<MangaLight>> =
        mutableStateOf(StateListWrapper.default())
    val contentSearch: MutableState<StateListWrapper<MangaLight>> = _contentSearch

    private var delayJob = Job()
    private var searchJob = Job()


}