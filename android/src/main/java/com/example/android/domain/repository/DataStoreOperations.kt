package com.example.android.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveLoginState(completed: Boolean)
    fun readOnLoginState(): Flow<Boolean>
}