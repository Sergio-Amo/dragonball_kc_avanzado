package com.kc.dragonball_kc_avanzado.data.local

import javax.inject.Inject

class InMemoryDataSource @Inject constructor() {
    private var token: String = ""

    fun getToken() = this.token

    fun saveToken(token: String) {
        this.token = token
    }

    fun removeToken() {
        this.token = ""
    }

}