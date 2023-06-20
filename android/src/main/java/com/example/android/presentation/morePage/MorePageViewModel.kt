package com.example.android.presentation.morePage

//import com.example.android.domain.usecases.GetPagingMangaUseCase
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.example.common.usecase.manga.GetMangaUseCase
import kotlinx.coroutines.flow.onEach

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