package com.example.common.usecase.manga

import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.MangaRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.mangaResponse.chapters.ChaptersLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMangaChaptersUseCase(private val repository: MangaRepository) {
    operator fun invoke(
        pageNum: Int = 0,
        pageSize: Int = 24,
        mangaId: String
    ): Flow<StateListWrapper<ChaptersLight>> {
        return flow {
            emit(StateListWrapper.loading())
            val state = when (val res = repository.getMangaChapters(
                pageNum = pageNum,
                pageSize = pageSize,
                mangaId = mangaId
            )) {
                is Resource.Success -> {
                    val data = res.data?.data!!
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