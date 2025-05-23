package com.example.common.usecase.content


import com.example.common.core.enum.ContentType
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.AnimeRepository
import com.example.common.data.repository.MangaRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentDetail
import com.example.common.util.resolveContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailsUseCase(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository
){
    operator fun invoke(contentType: String?, id: String): Flow<StateListWrapper<ContentDetail>> {
        return flow {
            emit(StateListWrapper.loading())
            val res = when(ContentType.valueOf(contentType ?: "NoValue")) {
                ContentType.Anime -> animeRepository.getAnimeDetails(id)
                ContentType.Manga -> mangaRepository.getMangaDetails(id)
            }

            println("DETAIL DETAIL DETAIL")

            val state = when(res) {
                is Resource.Success -> {
                    StateListWrapper(data = res.data?.data?.map {
                        resolveContentType<ContentDetail>(it)
                    } ?: emptyList())
                }
                is Resource.Error -> {
                    StateListWrapper(error = Event(res.message))
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}