package com.kc.dragonball_kc_avanzado.utils

import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.*

import org.junit.Test

class CoordinateValidatorTest {

    //SUT
    val coordinateValidator = CoordinateValidator()

    @Test
    fun `WHEN lat in range -90 to 90 and lng in range -180 to 180 THEN true`() {
        // Valid coordinates
        // Valid emails
        arrayOf(
            LatLng(38.8951, -77.0364), // Washington DC
            LatLng(51.5074, -0.1278),  // London, UK
            LatLng(48.8566, 2.3522),   // Paris, France
            LatLng(40.7128, -74.0060), // New York, USA
            LatLng(34.0522, -118.2437),// Los Angeles, USA
            LatLng(41.9028, 12.4964),  // Rome, Italy
            LatLng(-33.8688, 151.2093),// Sydney, Australia
            LatLng(35.6895, 139.6917), // Tokyo, Japan
            LatLng(55.7558, 37.6176),  // Moscow, Russia
            LatLng(-22.9068, -43.1729),// Rio de Janeiro, Brazil
            LatLng(39.9042, 116.4074),  // Beijing, China
        ).map {
            assertEquals(true, coordinateValidator.validateLatLng(it))
        }
    }
}