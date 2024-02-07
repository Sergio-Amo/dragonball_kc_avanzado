package com.kc.dragonball_kc_avanzado.data.mappers

import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor() {
    fun map(remote: List<HeroRemote>): List<HeroLocal> =
        remote.map { HeroLocal(it.id, it.name, it.photo, it.favorite, it.description) }

}