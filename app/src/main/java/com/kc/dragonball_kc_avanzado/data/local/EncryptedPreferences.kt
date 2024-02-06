package com.kc.dragonball_kc_avanzado.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EncryptedPreferences @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val LOGIN_TOKEN_VALUE = "LOGIN_TOKEN_VALUE"
    }

    private val masterKey =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

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