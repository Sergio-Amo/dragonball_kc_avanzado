package com.kc.dragonball_kc_avanzado.data

import android.util.Log
import com.kc.dragonball_kc_avanzado.data.local.LocalDataSource
import com.kc.dragonball_kc_avanzado.data.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun getToken(): String? {
        return localDataSource.getToken()
    }
    private suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }

    suspend fun deleteToken() {
        localDataSource.deleteToken()
    }

    suspend fun login(email: String, password: String): Boolean {
        val token = remoteDataSource.login(email, password)
        Log.d("TOKEN", token)
        if (token.isNotEmpty()){
            Log.d("TOKEN", token)
            saveToken(token)
            return true
        }
        return false
    }
}