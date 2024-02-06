package com.kc.dragonball_kc_avanzado.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EncryptedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val LOGIN_TOKEN_VALUE = "LOGIN_TOKEN_VALUE"
    }

    fun saveToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(LOGIN_TOKEN_VALUE, token)
            apply()
        }
    }

    fun deleteToken() {
        saveToken("")
    }

    fun getToken() = sharedPreferences.getString(LOGIN_TOKEN_VALUE, null)
}