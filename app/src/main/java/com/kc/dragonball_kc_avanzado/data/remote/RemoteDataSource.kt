package com.kc.dragonball_kc_avanzado.data.remote

import okhttp3.Credentials
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: DragonBallApi) {
    suspend fun login(email: String, password: String): String =
        api.login(Credentials.basic(email, password))

}