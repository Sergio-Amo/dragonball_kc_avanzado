package com.kc.dragonball_kc_avanzado.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val preferences: EncryptedPreferences) :
    LocalDataSourceInterface {
    override fun getToken(): String? {
        return preferences.getToken()
    }

    override fun saveToken(token: String) {
        preferences.saveToken(token)
    }

    override fun deleteToken() {
        preferences.deleteToken()
    }
}