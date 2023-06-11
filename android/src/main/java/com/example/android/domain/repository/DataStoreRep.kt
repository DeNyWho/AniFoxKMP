package com.example.android.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRep {

    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun logout()
    suspend fun saveLoginState(completed: Boolean)
    fun readOnLoginState(): Flow<Boolean>
}