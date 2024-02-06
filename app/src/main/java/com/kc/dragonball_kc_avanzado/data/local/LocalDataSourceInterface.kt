package com.kc.dragonball_kc_avanzado.data.local

interface LocalDataSourceInterface {
    fun getToken(): String?
    fun saveToken(token: String)
    fun deleteToken()

    // TODO: get heroes, details, locations ....
}