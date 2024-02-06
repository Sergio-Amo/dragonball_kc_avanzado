package com.kc.dragonball_kc_avanzado.data.remote

import retrofit2.http.Header
import retrofit2.http.POST

interface DragonBallApi {

    @POST("api/auth/login")
    suspend fun login(@Header("Authorization") credentials: String): String

}
