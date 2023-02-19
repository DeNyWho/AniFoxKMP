package com.example.common.usecase.manga

import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.models.GenreRequest
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper
import com.example.common.repository.MangaRepository
import com.example.common.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRomanceMangaUseCase(private val repository: MangaRepository) {
    operator fun invoke(): Flow<StateListWrapper<MangaLight>> {
        return flow {
            emit(StateListWrapper.loading())
            println(listOf(Constants.romance, Constants.dramma))
            val state = when (val res = repository.getManga(
                order = null, pageNum = 0, pageSize = 12, status = null, genres =
                listOf(
                    Constants.romance,
                    Constants.dramma,
                    Constants.sedze
                )
            )) {
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