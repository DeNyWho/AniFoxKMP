package com.example.android.domain.usecases

import com.example.android.domain.repository.DataStoreRepository
import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.AuthRepository
import com.example.common.domain.common.StateListWrapper
import com.example.common.domain.common.StateMapWrapper
import com.example.common.models.animeResponse.light.toContentLight
import com.example.common.models.auth.TokenResponse
import com.example.common.util.ExtractCookie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(
    private val repository: AuthRepository,
    private val dataStoreOperations: DataStoreRepository
){
    operator fun invoke(email: String, password: String): Flow<StateListWrapper<TokenResponse>> {
        return flow {
            emit(StateListWrapper.loading())
            val state = when(val res = repository.login(email, password)) {
                is Resource.Success -> {
                    dataStoreOperations.saveToken(token = res.data?.accessToken ?: "")
                    StateListWrapper(data = listOf(res.data!!))
                }
                is Resource.Error -> {
                    StateListWrapper( error = Event(res.message))
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }
            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}