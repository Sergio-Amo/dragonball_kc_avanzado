package com.kc.dragonball_kc_avanzado.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.kc.dragonball_kc_avanzado.data.Repository
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocation
import com.kc.dragonball_kc_avanzado.utils.CoordinateValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Error
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val coordinateValidator: CoordinateValidator,
) : ViewModel() {

    private val _hero = MutableLiveData<HeroDetail>()
    val hero: LiveData<HeroDetail> get() = _hero

    private val _locations = MutableLiveData<List<HeroLocation>>()
    val locations: LiveData<List<HeroLocation>> get() = _locations

    fun getHero(name: String) {
        viewModelScope.launch {
            val hero = withContext(Dispatchers.IO) {
                repository.getHeroDetail(name)
            }

            _hero.value = hero
        }
    }

    fun toggleFavorite() {
        _hero.value?.let {
            it.favorite = !it.favorite
            _hero.value = it
            viewModelScope.launch(Dispatchers.IO) { repository.toggleFavorite(it.id) }
        }
    }

    fun getHeroLocation() {
        viewModelScope.launch {
            hero.value?.let {
                val rawLocations = withContext(Dispatchers.IO) {
                    repository.getLocations(it.id)
                }
                val filteredLocation = rawLocations.filter {
                    try {
                        coordinateValidator.validateLatLng(
                            LatLng(it.latitud.toDouble(), it.longitud.toDouble())
                        )
                    } catch (err: Error) {
                        false
                    }
                }
                _locations.value = filteredLocation.map {
                    HeroLocation(
                        LatLng(it.latitud.toDouble(), it.longitud.toDouble()),
                        LocalDateTime.parse(it.dateShow, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'"))
                    )
                }
            }
        }
    }
}