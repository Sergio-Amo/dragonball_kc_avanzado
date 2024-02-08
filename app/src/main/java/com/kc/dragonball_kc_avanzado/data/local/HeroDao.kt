package com.kc.dragonball_kc_avanzado.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal

@Dao
interface HeroDAO {
    @Query("SELECT * FROM heroes")
    fun getHeroes(): List<HeroLocal>

    @Query("SELECT * FROM heroes WHERE id = :id LIMIT 1")
    fun getHeroById(id: String): HeroLocal

    @Insert
    fun saveHeroes(heroes: List<HeroLocal>)

    @Update
    fun updateHero(hero: HeroLocal)

    @Query("DELETE FROM heroes")
    suspend fun deleteHeroes()

}