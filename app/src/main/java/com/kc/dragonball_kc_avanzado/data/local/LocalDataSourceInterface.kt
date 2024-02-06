package com.kc.dragonball_kc_avanzado.data.local

interface LocalDataSourceInterface {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun deleteToken()

    // TODO: get heroes, details, locations ....
}