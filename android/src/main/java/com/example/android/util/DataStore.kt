package com.example.android.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.android.util.Constants.ACCESS_TOKEN_KEY

class DataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("anifox_manager")

    suspend fun updateSession(access: String, refresh: String) {
        val accessToken = stringPreferencesKey(ACCESS_TOKEN_KEY)
        val refreshToken = stringPreferencesKey(ACCESS_TOKEN_KEY)
        context.dataStore.edit { preferences ->
            preferences[accessToken] = access
            preferences[refreshToken] = refresh
        }
    }
    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }
}