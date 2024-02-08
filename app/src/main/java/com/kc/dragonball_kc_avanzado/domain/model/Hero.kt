package com.kc.dragonball_kc_avanzado.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime


@Entity(tableName = "heroes")
data class HeroLocal(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo") val photo: URL,
    @ColumnInfo(name = "favorite") var favorite: Boolean,
    @ColumnInfo(name = "description") val description: String,
)


data class HeroRemote(
    val id: String,
    val name: String,
    val photo: String,
    var favorite: Boolean,
    val description: String,
)

data class HeroList(
    val id: String,
    val name: String,
    val photo: URL,
    var favorite: Boolean,
)

data class HeroDetail(
    val id: String,
    val name: String,
    val photo: URL,
    var favorite: Boolean,
    val description: String,
)

data class HeroLocationsRemote(
    val longitud: String,
    val dateShow: String,
    val latitud: String,
)

data class HeroLocation(
    val latLng: LatLng,
    val dateShow: LocalDateTime,
)
