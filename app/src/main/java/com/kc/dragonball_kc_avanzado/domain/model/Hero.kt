package com.kc.dragonball_kc_avanzado.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// name photo favorite id description
@Entity(tableName = "heros")
data class HeroLocal(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(tableName = "heros")
data class HeroRemote(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean,
    @ColumnInfo(name = "description") val description: String,
)

data class HeroList(
    val id: String,
    val name: String,
    val photo: String,
    val favorite: Boolean,
)

data class HeroDetail(
    val id: String,
    val name: String,
    val photo: String,
    val favorite: Boolean,
    val description: String,
)


fun HeroRemote.mapToLocal(): HeroLocal {
    return HeroLocal(id, name, photo, favorite, description)
}
