package com.kc.dragonball_kc_avanzado.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kc.dragonball_kc_avanzado.data.local.converters.UrlConverter
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal

@Database(entities = [HeroLocal::class], version = 1)
@TypeConverters(UrlConverter::class)
abstract class HeroDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDAO
}