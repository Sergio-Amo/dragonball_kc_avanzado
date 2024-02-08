package com.kc.dragonball_kc_avanzado.data.mappers

import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import javax.inject.Inject

class LocalToDetailMapper @Inject constructor() {
    fun map(local: List<HeroLocal>): List<HeroDetail> =
        local.map { HeroDetail(it.id, it.name, it.photo, it.favorite, it.description) }

}