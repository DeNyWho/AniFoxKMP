package com.example.common.usecase.user

import com.example.common.core.enum.ContentType
import com.example.common.core.enum.StatusListType
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.UserRepository
import com.example.common.domain.common.StateListWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SetFavoriteListUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(
        contentType: String?,
        statusListType: StatusListType,
        url: String,
        token: String,
    ): Flow<StateListWrapper<String>> {
        return flow {
            emit(StateListWrapper.loading())
            println("WAFL = ${userRepository.setAnimeFavoriteList(token, url, statusListType.name)}")
            val res = when (ContentType.valueOf(contentType ?: "NoValue")) {
                ContentType.Anime -> userRepository.setAnimeFavoriteList(token, url, statusListType.name)
                ContentType.Manga -> userRepository.setMangaFavoriteList(token, url, statusListType.name)
            }
            val state = when (res) {
                is Resource.Success -> {
                    when(res.message) {
                        "OK" -> {
                            StateListWrapper(
                                data = listOf("Created"),
                                isLoading = false
                            )
                        }
                        "Created" -> {
                            StateListWrapper(
                                data = listOf("Created"),
                                isLoading = false
                            )
                        }
                        else -> {
                            StateListWrapper(
                                data = listOf("Empty"),
                                isLoading = false
                            )
                        }
                    }
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