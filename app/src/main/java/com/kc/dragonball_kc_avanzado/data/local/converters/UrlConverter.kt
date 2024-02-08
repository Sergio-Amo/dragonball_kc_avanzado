package com.kc.dragonball_kc_avanzado.data.local.converters

import androidx.room.TypeConverter
import java.net.URL

class UrlConverter {
    @TypeConverter
    fun fromURL(value: URL): String {
        return value.toString()
    }

    @TypeConverter
    fun toURL(value: String): URL {
        return URL(value)
    }
}