package com.kc.dragonball_kc_avanzado.data

import com.kc.dragonball_kc_avanzado.data.local.LocalDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    //private val remoteDataSource: RemoteDataSource,
) {
    suspend fun getToken(): String? {
        return localDataSource.getToken()
    }
    suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }

    suspend fun deleteToken() {
        localDataSource.deleteToken()
    }
}