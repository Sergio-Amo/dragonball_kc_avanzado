package com.kc.dragonball_kc_avanzado.data

import android.util.Log
import com.kc.dragonball_kc_avanzado.data.local.InMemoryDataSource
import com.kc.dragonball_kc_avanzado.data.local.LocalDataSource
import com.kc.dragonball_kc_avanzado.data.mappers.LocalToListMapper
import com.kc.dragonball_kc_avanzado.data.mappers.RemoteToLocalMapper
import com.kc.dragonball_kc_avanzado.data.remote.RemoteDataSource
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroList
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val localToListMapper: LocalToListMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val inMemoryDataSource: InMemoryDataSource,
) {
    suspend fun getToken(): String? =
        inMemoryDataSource.getToken().ifEmpty { localDataSource.getToken() }

    fun isPersistentSession(): Boolean = inMemoryDataSource.getToken().isNotEmpty()

    private suspend fun saveToken(token: String, persist: Boolean) {
        if (persist)
            localDataSource.saveToken(token)
        else
            inMemoryDataSource.saveToken(token)
    }

    suspend fun deleteToken() {
        localDataSource.deleteToken()
    }

    suspend fun login(email: String, password: String, remember: Boolean): Boolean {
        val token = remoteDataSource.login(email, password)
        Log.d("TOKEN", token)
        if (token.isNotEmpty()) {
            saveToken(token, remember)
            return true
        }
        return false
    }

    suspend fun getHeroes(): List<HeroList> {
        val heroesLocal: List<HeroLocal> = localDataSource.getHeroes()
        return if (heroesLocal.isNotEmpty()) {
            localToListMapper.map(heroesLocal)
        } else {
            val token = "Bearer ${getToken()}"
            val heroesRemote: List<HeroRemote> = remoteDataSource.getHeroes(token)
            localDataSource.saveHeroes(remoteToLocalMapper.map(heroesRemote))
            localToListMapper.map(localDataSource.getHeroes())
        }
    }

    suspend fun getHeroDetail(name: String): HeroDetail {
        val token = "Bearer ${getToken()}"
        return remoteDataSource.getHeroDetail(name, token)
    }
}