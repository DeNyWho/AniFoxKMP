package com.example.android.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.usecases.ReadTokenUseCase
import com.example.common.core.enum.StatusListType
import com.example.common.domain.common.StateListWrapper
import com.example.common.domain.common.StateWrapper
import com.example.common.models.common.ContentDetail
import com.example.common.models.common.ContentLight
import com.example.common.models.common.ContentMedia
import com.example.common.usecase.anime.GetAnimeMediaUseCase
import com.example.common.usecase.anime.GetAnimeScreenshotsUseCase
import com.example.common.usecase.content.GetDetailsUseCase
import com.example.common.usecase.content.GetRelatedUseCase
import com.example.common.usecase.content.GetSimilarUseCase
import com.example.common.usecase.user.SetFavoriteListUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val getAnimeScreenshots: GetAnimeScreenshotsUseCase,
    private val getAnimeMedia: GetAnimeMediaUseCase,
    private val getAnimeSimilar: GetSimilarUseCase,
    private val getAnimeRelated: GetRelatedUseCase,
    private val readTokenUseCase: ReadTokenUseCase,
    private val setFavoriteListUseCase: SetFavoriteListUseCase,
): ViewModel() {

    private val _detailAnime: MutableState<StateListWrapper<ContentDetail>> =
        mutableStateOf(StateListWrapper.default())
    val detailAnime: MutableState<StateListWrapper<ContentDetail>> = _detailAnime

    private val _screenshots: MutableState<StateListWrapper<String>> =
        mutableStateOf(StateListWrapper.default())
    val screenshots: MutableState<StateListWrapper<String>> = _screenshots

    private val _media: MutableState<StateListWrapper<ContentMedia>> =
        mutableStateOf(StateListWrapper.default())
    val media: MutableState<StateListWrapper<ContentMedia>> = _media

    private val _similar: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val similar: MutableState<StateListWrapper<ContentLight>> = _similar

    private val _related: MutableState<StateListWrapper<ContentLight>> =
        mutableStateOf(StateListWrapper.default())
    val related: MutableState<StateListWrapper<ContentLight>> = _related

    private val _token: MutableState<StateWrapper<String>> =
        mutableStateOf(StateWrapper.loading())
    val token: MutableState<StateWrapper<String>> = _token

    init {
        fetchToken()
    }

    private fun fetchToken() {
        viewModelScope.launch {
            readTokenUseCase.invoke().collect { token ->
                _token.value = token
            }
        }
    }

    fun setList(status: StatusListType, contentType: String?, url: String) {
        println("WW 12 = $status")
        println("WW = $contentType")
        println("WW = $url")
        println("WW = ${token.value.data}")
        setFavoriteListUseCase.invoke(contentType = contentType, token = token.value.data!!, url = url, statusListType = status).onEach {

        }.launchIn(viewModelScope)
    }

    fun getSimilar(contentType: String?, url: String) {
        getAnimeSimilar.invoke(contentType, url).onEach {
            _similar.value = it
        }.launchIn(viewModelScope)
    }

    fun getRelated(contentType: String?, url: String) {
        getAnimeRelated.invoke(contentType, url).onEach {
            _related.value = it
        }.launchIn(viewModelScope)
    }

    fun getDetail(contentType: String?, id: String){
        getDetailsUseCase.invoke(contentType, id).onEach {
            _detailAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getScreenshots(url: String){
        getAnimeScreenshots.invoke(url).onEach {
            _screenshots.value = it
        }.launchIn(viewModelScope)
    }

    fun getMedia(url: String){
        getAnimeMedia.invoke(url).onEach {
            _media.value = it
        }.launchIn(viewModelScope)
    }

}