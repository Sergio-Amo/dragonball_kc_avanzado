package com.kc.dragonball_kc_avanzado.data.local

import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val preferences: EncryptedPreferences,
    private val dao: HeroDAO,
) {
    suspend fun getToken(): String? {
        return preferences.getToken()
    }

    suspend fun saveToken(token: String) {
        preferences.saveToken(token)
    }

    suspend fun deleteToken() {
        preferences.deleteToken()
    }

    suspend fun getHeroes(): List<HeroLocal> {
        return dao.getHeroes()
    }

    suspend fun saveHeroes(heroes: List<HeroLocal>) {
        dao.saveHeroes(heroes)
    }
}