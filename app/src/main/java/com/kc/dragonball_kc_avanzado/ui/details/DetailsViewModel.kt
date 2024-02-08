package com.kc.dragonball_kc_avanzado.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.dragonball_kc_avanzado.data.Repository
import com.kc.dragonball_kc_avanzado.data.mappers.HeroLocationsRemoteToHeroLocationMapper
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val heroLocationsRemoteToHeroLocationMapper: HeroLocationsRemoteToHeroLocationMapper,
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
        val id: String = hero.value?.id ?: return
        viewModelScope.launch {
            val raw = withContext(Dispatchers.IO) {
                hero.value?.run { repository.getLocations(id) }
            }
            raw?.let { _locations.value = heroLocationsRemoteToHeroLocationMapper.map(it) }
        }
    }
}