package com.example.common.usecase.anime

import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.AnimeRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.animeResponse.light.toContentLight
import com.example.common.models.common.ContentLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAnimeUseCase(private val repository: AnimeRepository) {
    operator fun invoke(
        season: String? = null,
        ratingMpa: String? = null,
        minimalAge: String? = null,
        type: String? = null,
        order: String? = null,
        pageNum: Int = 0,
        pageSize: Int = 24,
        status: String? = null,
        genres: List<String>? = null,
        searchQuery: String? = null,
        year: Int? = null
    ): Flow<StateListWrapper<ContentLight>> {
        return flow {
            emit(StateListWrapper.loading())
            val state = when (val res = repository.getAnime(
                order = order,
                pageNum = pageNum,
                pageSize = pageSize,
                status = status,
                genres = genres,
                searchQuery = searchQuery,
                minimalAge = minimalAge,
                ratingMpa = ratingMpa,
                season = season,
                type = type,
                year = year
            )) {
                is Resource.Success -> {
                    val data = res.data?.data?.map {
                        it.toContentLight()
                    } .orEmpty()
                    println(StateListWrapper(data.take(2)))
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