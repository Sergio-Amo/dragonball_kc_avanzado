package com.kc.dragonball_kc_avanzado.data.local

import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal

interface LocalDataSourceInterface {
    suspend fun getToken(): String?

    suspend fun saveToken(token: String)

    suspend fun deleteToken()

    suspend fun getHeroes(): List<HeroLocal>

    suspend fun getHeroById(id: String): HeroLocal

    suspend fun saveHeroes(heroes: List<HeroLocal>)

    suspend fun toggleFavorite(heroId: String)

    suspend fun deleteHeroes()
}