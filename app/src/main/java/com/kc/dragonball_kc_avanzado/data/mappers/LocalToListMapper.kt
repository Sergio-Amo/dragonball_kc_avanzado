package com.kc.dragonball_kc_avanzado.data.mappers

import com.kc.dragonball_kc_avanzado.domain.model.HeroList
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import javax.inject.Inject

class LocalToListMapper @Inject constructor() {
    fun map(local: List<HeroLocal>): List<HeroList> =
        local.map { HeroList(it.id, it.name, it.photo, it.favorite) }

}