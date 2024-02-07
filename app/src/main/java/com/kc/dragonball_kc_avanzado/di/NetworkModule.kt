package com.kc.dragonball_kc_avanzado.di

import com.kc.dragonball_kc_avanzado.data.remote.DragonBallApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModuleModule {

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    fun providesRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder().baseUrl("https://dragonball.keepcoding.education/")
            .addConverterFactory(ScalarsConverterFactory.create()) // FFFFFUUUUUUU
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    }

    @Provides
    fun providesDragonBallApi(retrofit: Retrofit): DragonBallApi {
        return retrofit.create(DragonBallApi::class.java)
    }
}