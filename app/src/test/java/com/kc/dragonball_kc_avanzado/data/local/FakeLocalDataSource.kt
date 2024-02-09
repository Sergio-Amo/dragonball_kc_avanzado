package com.kc.dragonball_kc_avanzado.data.local

import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal

class FakeLocalDataSource : LocalDataSourceInterface {

    private var heroesLocal = mutableListOf<HeroLocal>()
    private var token = "token"

    override suspend fun saveHeroes(heroes: List<HeroLocal>) {
        heroesLocal.addAll(heroes)
    }

    override suspend fun getHeroes(): List<HeroLocal> {
        return heroesLocal
    }

    override suspend fun getToken(): String? {
        return token
    }

    override suspend fun saveToken(token: String) {
        this.token = token
    }

    override suspend fun deleteToken() {
        token = ""
    }

    override suspend fun getHeroById(id: String): HeroLocal {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(heroId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHeroes() {
        TODO("Not yet implemented")
    }

}