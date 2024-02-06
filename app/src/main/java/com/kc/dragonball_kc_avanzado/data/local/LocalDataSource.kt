package com.kc.dragonball_kc_avanzado.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val preferences: EncryptedPreferences) :
    LocalDataSourceInterface {
    override suspend fun getToken(): String? {
        return preferences.getToken()
    }

    override suspend fun saveToken(token: String) {
        preferences.saveToken(token)
    }

    override suspend fun deleteToken() {
        preferences.deleteToken()
    }
}