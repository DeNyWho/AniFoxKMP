package com.example.common.usecase.manga

import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper
import com.example.common.repository.MangaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLinkedUseCase(private val repository: MangaRepository){
    operator fun invoke(id: String): Flow<StateListWrapper<MangaLight>>{
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val res = repository.getMangaLinked(id)) {
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