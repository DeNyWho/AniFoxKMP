package com.example.common.usecase.manga

import com.example.common.core.enum.ContentType
import com.example.common.core.exception.MyError
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.domain.common.StateListWrapper
import com.example.common.data.repository.MangaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailsUseCase(private val mangaRepository: MangaRepository){
    operator fun invoke(contentType: String?, id: String): Flow<StateListWrapper<MangaDetail>> {
        return flow {
            emit(StateListWrapper.loading())
            println("ID = $id")
            val res = when(ContentType.valueOf(contentType ?: "NoValue")) {
//                ContentType.Anime -> animeRepository.getAnimeDetails(id)
                ContentType.Manga -> mangaRepository.getMangaDetails(id)
                else -> Resource.Error(MyError.UNKNOWN_ERROR)
            }
            val state = when (res) {
                is Resource.Success -> {
                    val data = res.data?.data.orEmpty()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = Event(res.message))
                }
                is Resource.Loading -> StateListWrapper.loading()
            }
            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}