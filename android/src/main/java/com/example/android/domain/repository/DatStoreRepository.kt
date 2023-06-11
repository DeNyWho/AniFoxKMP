package com.example.android.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.android.domain.repository.DataStoreRepository.PreferencesKey.accessToken
import com.example.android.util.Constants.ACCESS_TOKEN
import com.example.android.util.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreRepository (context: Context): DataStoreRep {

    private object PreferencesKey {
        val onLoginKey = booleanPreferencesKey(name = PREFERENCES_NAME)
        val accessToken = stringPreferencesKey(name = ACCESS_TOKEN)
    }

    private val dataStore = context.datastore

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[accessToken] = token
        }
    }

    override suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[accessToken]
    }

    override suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun saveLoginState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onLoginKey] = completed
        }
    }

    override fun readOnLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onLoginState = preferences[PreferencesKey.onLoginKey] ?: false
                onLoginState
            }
    }
}