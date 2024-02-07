package com.kc.dragonball_kc_avanzado.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal

@Dao
interface HeroDAO {
    @Query("SELECT * FROM heros")
    fun getHeroes(): List<HeroLocal>

    @Insert
    fun saveHeroes(heros: List<HeroLocal>)
}