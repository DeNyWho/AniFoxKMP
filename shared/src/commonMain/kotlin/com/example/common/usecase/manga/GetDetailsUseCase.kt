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

class GetDetailsUseCase(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository
){
    operator fun invoke(contentType: String?, id: String): Flow<StateListWrapper<ContentDetail>> {
        return flow {
            println("WADSADSA = $contentType")
            emit(StateListWrapper.loading())
            val res = when(ContentType.valueOf(contentType ?: "NoValue")) {
                ContentType.Anime -> animeRepository.getAnimeDetails(id)
                ContentType.Manga -> mangaRepository.getMangaDetails(id)
            }
            val state = when(res) {
                is Resource.Success -> {
                    StateListWrapper(data = resolveContentType(res.data?.data?.get(0)))
                }
                is Resource.Error -> {
                    StateListWrapper(error = Event(res.message))
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            println("DFSFDSFSD = ${res.data?.data}")

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    private fun resolveContentType(data: Any?): List<ContentDetail> {
        return when (data) {
            is AnimeDetail -> listOf(data.toContentDetail())
            is MangaDetail -> {
                println("ВАЫАЫВАЫВАЫВЧСЯСЧЯСФЫВФЫ")
                listOf(data.toContentDetail())
            }
            else -> {
                println("FDSFSDFGSDJKFXZNKCKJZNXCASDASDAS")
                listOf(ContentDetail())
            }
        }
    }
}