package com.kc.dragonball_kc_avanzado.data.remote

import com.kc.dragonball_kc_avanzado.data.remote.request.HeroRequest
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import okhttp3.Credentials
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: DragonBallApi) {
    suspend fun login(email: String, password: String): String =
        api.login(Credentials.basic(email, password))

    suspend fun getHeroes(token: String): List<HeroRemote> = api.getHeroes(HeroRequest(), token)

    suspend fun getHeroDetail(name: String, token: String): HeroDetail =
        api.getHeroesDetail(HeroRequest(name), token).first()

}