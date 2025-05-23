package com.example.common.usecase.anime

import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.AnimeRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAnimeMediaUseCase(private val repository: AnimeRepository){
    operator fun invoke(url: String): Flow<StateListWrapper<ContentMedia>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val res = repository.getAnimeMedia(url)) {
                is Resource.Success -> {
                    val data = res.data?.data?.map {
                        it
                    } .orEmpty()

                    println("ZXC D = $data")

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