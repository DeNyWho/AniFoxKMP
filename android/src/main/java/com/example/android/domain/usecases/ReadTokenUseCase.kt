package com.example.android.domain.usecases

import com.example.android.domain.repository.DataStoreRepository
import com.example.common.domain.common.StateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReadTokenUseCase (
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<StateWrapper<String>> {
        return flow {
            emit(StateWrapper.loading())
            println("ZXC CURSED D = ${dataStoreRepository.getToken()}")
            emit(StateWrapper(isLoading = false, data = dataStoreRepository.getToken()))
        }.flowOn(Dispatchers.IO)
    }
}
