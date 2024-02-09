package com.kc.dragonball_kc_avanzado.data.local

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EncryptedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val LOGIN_TOKEN_VALUE = "LOGIN_TOKEN_VALUE"
    }

    suspend fun saveToken(token: String) {
        withContext(Dispatchers.IO) {
            with(sharedPreferences.edit()) {
                putString(LOGIN_TOKEN_VALUE, token)
                apply()
            }
        }
    }

    suspend fun deleteToken() {
        saveToken("")
    }

    suspend fun getToken() = withContext(Dispatchers.IO) {
        sharedPreferences.getString(LOGIN_TOKEN_VALUE, null)
    }
}