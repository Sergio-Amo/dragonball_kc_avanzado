package com.kc.dragonball_kc_avanzado.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "heroes")
data class HeroLocal(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo") val photo: String,
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
    val photo: String,
    var favorite: Boolean,
)

data class HeroDetail(
    val id: String,
    val name: String,
    val photo: String,
    var favorite: Boolean,
    val description: String,
)
