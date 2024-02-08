package com.kc.dragonball_kc_avanzado.data.remote

import com.kc.dragonball_kc_avanzado.data.remote.request.FavoriteRequest
import com.kc.dragonball_kc_avanzado.data.remote.request.HeroRequest
import com.kc.dragonball_kc_avanzado.data.remote.request.LocationsRequest
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocationsRemote
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DragonBallApi {

    @POST("api/auth/login")
    suspend fun login(@Header("Authorization") credentials: String): String

    @POST("api/heros/all")
    suspend fun getHeroes(@Body request: HeroRequest, @Header("Authorization") credentials: String): List<HeroRemote>

    @POST("api/heros/all")
    suspend fun getHeroesDetail(@Body request: HeroRequest, @Header("Authorization") credentials: String): List<HeroDetail>

    @POST("api/data/herolike")
    suspend fun toggleFavorite(@Body request: FavoriteRequest, @Header("Authorization") credentials: String)

    @POST("api/heros/locations")
    suspend fun getLocations(@Body request: LocationsRequest, @Header("Authorization") credentials: String): List<HeroLocationsRemote>

}
