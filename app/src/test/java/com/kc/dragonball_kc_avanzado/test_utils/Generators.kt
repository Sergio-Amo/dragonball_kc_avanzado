package com.kc.dragonball_kc_avanzado.test_utils

import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocationsRemote
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import java.net.URL


fun generateHeroLocal(size: Int) = (0 until size).map {
    HeroLocal(
        "id$it", "HeroLocal$it", URL("https://url$it.com"), false, "HeroLocal"
    )
}

fun generateHeroRemote(size: Int) = (0 until size).map {
    HeroRemote(
        "id$it", "HeroRemote$it", "https://url$it.com", false, "HeroRemote"
    )
}

fun generateHeroDetail() = HeroDetail("id", "HeroDetail", URL("http://asd.es"), true, "description")

fun generateHeroLocationsRemote(): List<HeroLocationsRemote> {
    return listOf(
        HeroLocationsRemote("2.0701494", "2022-09-11T00:00:00Z", "41.3926467"),
        HeroLocationsRemote("-74.2605503", "2022-09-26T00:00:00Z", "40.6976684"),
    )
}