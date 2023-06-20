package com.example.common.usecase.user

import com.example.common.core.enum.ContentType
import com.example.common.core.enum.StatusListType
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.UserRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.example.common.util.Constants
import com.example.common.util.resolveContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetFavoriteListUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(
        contentType: String?,
        statusListType: StatusListType,
        token: String,
        pageNum: Int,
        pageSize: Int = Constants.FavoriteListSize
    ): Flow<StateListWrapper<ContentLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val res = when (ContentType.valueOf(contentType ?: "NoValue")) {
                ContentType.Anime -> userRepository.getAnimeFavoriteList(pageNum, pageSize, token, statusListType.name)
                ContentType.Manga -> userRepository.getMangaFavoriteList(pageNum, pageSize, token, statusListType.name)
            }

            val state = when (res) {
                is Resource.Success -> {
                    val a = StateListWrapper(data = res.data?.map {
                        resolveContentType<ContentLight>(it)
                    } ?: emptyList())
                    if (a.data.isEmpty()) {
                        StateListWrapper(data = emptyList(), isLoading = false)
                    } else StateListWrapper(
                        data = res.data?.map {
                            resolveContentType<ContentLight>(it)
                        } ?: emptyList(),
                        isLoading = false
                    )
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