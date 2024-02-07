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

    private suspend fun getHeroById(id: String): HeroLocal {
        return dao.getHeroById(id)
    }

    suspend fun saveHeroes(heroes: List<HeroLocal>) {
        dao.saveHeroes(heroes)
    }

    suspend fun toggleFavorite(heroId: String) {
        val hero = getHeroById(heroId)
        hero.favorite = !hero.favorite
        dao.updateHero(hero)
    }
}