package com.kc.dragonball_kc_avanzado.data.mappers

import com.kc.dragonball_kc_avanzado.domain.model.HeroLocal
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import java.net.URL
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor() {
    fun map(remote: List<HeroRemote>): List<HeroLocal> =
        remote.map {
            val url = URL(it.photo)
            val photo = if (url.protocol == "http")
                URL("https", url.host, url.file)
            else url
            HeroLocal(it.id, it.name, photo, it.favorite, it.description)
        }
}