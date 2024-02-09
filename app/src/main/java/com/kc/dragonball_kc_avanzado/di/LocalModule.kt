package com.kc.dragonball_kc_avanzado.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.kc.dragonball_kc_avanzado.data.local.HeroDAO
import com.kc.dragonball_kc_avanzado.data.local.HeroDatabase
import com.kc.dragonball_kc_avanzado.data.local.LocalDataSource
import com.kc.dragonball_kc_avanzado.data.local.LocalDataSourceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            "dragon_ball_secret_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    @Provides
    fun providesHeroDatabase(@ApplicationContext context: Context): HeroDatabase {
        return Room.databaseBuilder(
            context,
            HeroDatabase::class.java, "hero-database"
        ).build()
    }

    @Provides
    fun providesHeroDao(db: HeroDatabase): HeroDAO {
        return db.heroDao()
    }

    @Provides
    fun providesLocalDataSourceInterface(source: LocalDataSource): LocalDataSourceInterface =
        source
}