package com.kc.dragonball_kc_avanzado.utils

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class CoordinateValidator @Inject constructor() {
    fun validateLatLng(coordinates: LatLng): Boolean {
        with(coordinates) {
            return if (latitude < -90 || latitude > 90) false
            else !(longitude < -180 || longitude > 180)
        }
    }
}