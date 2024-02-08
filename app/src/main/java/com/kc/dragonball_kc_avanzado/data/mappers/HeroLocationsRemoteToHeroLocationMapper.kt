package com.kc.dragonball_kc_avanzado.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocation
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocationsRemote
import com.kc.dragonball_kc_avanzado.utils.CoordinateValidator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HeroLocationsRemoteToHeroLocationMapper @Inject constructor(private val coordinateValidator: CoordinateValidator) {
    fun map(raw: List<HeroLocationsRemote>): List<HeroLocation> {
        val valid = raw.filter {
            coordinateValidator.validateLatLng(
                LatLng(
                    it.latitud.toDouble(), it.longitud.toDouble()
                )
            )
        }
        return valid.map {
            HeroLocation(
                LatLng(it.latitud.toDouble(), it.longitud.toDouble()),
                LocalDateTime.parse(
                    it.dateShow, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'")
                ),
            )
        }
    }
}