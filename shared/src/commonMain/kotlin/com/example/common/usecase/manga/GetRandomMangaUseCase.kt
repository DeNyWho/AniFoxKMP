package com.example.common.usecase.manga

import com.example.common.compose.RandomMangaState
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.repository.MangaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRandomMangaUseCase(private val repository: MangaRepository){
    operator fun invoke(): Flow<RandomMangaState>{
        return flow {
            emit(RandomMangaState(isLoading = true))

            val state = when (val res = repository.getManga(order = "random", pageNum = 0, pageSize = 12, status = null, genres = null)) {
                is Resource.Success -> {
                    val data = res.data?.data.orEmpty()

                    RandomMangaState(data)
                }
                is Resource.Error -> {
                    RandomMangaState(error = Event(res.message))
                }
                is Resource.Loading -> RandomMangaState(isLoading = true)
            }
            emit(state)
        }.flowOn(Dispatchers.IO)
    }

}