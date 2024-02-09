package com.kc.dragonball_kc_avanzado.data.local

import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val preferences: EncryptedPreferences,
    private val dao: HeroDAO,
) : LocalDataSourceInterface {
    override suspend fun getToken(): String? {
        return preferences.getToken()
    }

    override suspend fun saveToken(token: String) {
        preferences.saveToken(token)
    }

    override suspend fun deleteToken() {
        preferences.deleteToken()
    }

    override suspend fun getHeroes(): List<HeroLocal> {
        return dao.getHeroes()
    }

    override suspend fun getHeroById(id: String): HeroLocal {
        return dao.getHeroById(id)
    }

    override suspend fun saveHeroes(heroes: List<HeroLocal>) {
        dao.saveHeroes(heroes)
    }

    override suspend fun toggleFavorite(heroId: String) {
        val hero = getHeroById(heroId)
        hero.favorite = !hero.favorite
        dao.updateHero(hero)
    }

    override suspend fun deleteHeroes() {
        dao.deleteHeroes()
    }
}