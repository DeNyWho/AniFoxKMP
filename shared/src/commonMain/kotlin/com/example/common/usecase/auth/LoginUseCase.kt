package com.example.common.usecase.auth

import com.example.common.core.wrapper.Event
import com.example.common.core.wrapper.Resource
import com.example.common.data.repository.AuthRepository
import com.example.common.domain.common.StateMapWrapper
import com.example.common.util.ExtractCookie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(private val repository: AuthRepository){
    operator fun invoke(email: String, password: String): Flow<StateMapWrapper<String>> {
        return flow {
            emit(StateMapWrapper.loading())
            val state = when (val res = repository.login(email, password) ) {
                is Resource.Success -> {
                    val cook = res.cookie!!.joinToString(", ")
                    StateMapWrapper(ExtractCookie.extractCookieValues(cook))
                }
                is Resource.Error -> {
                    StateMapWrapper(error = Event(res.message))
                }
                is Resource.Loading -> StateMapWrapper.loading()
            }
            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}