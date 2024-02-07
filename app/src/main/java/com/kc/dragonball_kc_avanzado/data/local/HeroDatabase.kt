package com.kc.dragonball_kc_avanzado.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal

@Database(entities = [HeroLocal::class], version = 1)
abstract class HeroDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDAO
}