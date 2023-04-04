package com.example.common.usecase.manga

import com.example.common.core.enum.ContentType
import com.example.common.core.exception.MyError
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.AnimeRepository
import com.example.common.data.repository.MangaRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.animeResponse.detail.AnimeDetail
import com.example.common.models.animeResponse.detail.toContentDetail
import com.example.common.models.common.ContentDetail
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.detail.toContentDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailsUseCase(private val mangaRepository: MangaRepository, private val animeRepository: AnimeRepository){
    operator fun invoke(contentType: String?, id: String): Flow<StateListWrapper<ContentDetail>> {
        return flow {
            emit(StateListWrapper.loading())
            println("ID = $id")
            val res = when(ContentType.valueOf(contentType ?: "NoValue")) {
                ContentType.Anime -> animeRepository.getAnimeDetails(id)
                ContentType.Manga -> mangaRepository.getMangaDetails(id)
                else -> Resource.Error(MyError.UNKNOWN_ERROR)
            }
            val b = mangaRepository.getMangaDetails(id).data?.data
            val state = when (res) {
                is Resource.Success -> StateListWrapper(res.data!!.data!!.map {
                    resolveContentType(it)!!
                })
                is Resource.Error -> StateListWrapper(error = Event(res.message))
                is Resource.Loading -> StateListWrapper(isLoading = true)
            }
            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    private fun resolveContentType(data: Any?): ContentDetail? {
        return when (data) {
            is AnimeDetail -> data.toContentDetail()
            is MangaDetail -> data.toContentDetail()
            else -> null
        }
    }
}