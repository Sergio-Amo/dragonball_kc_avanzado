package com.kc.dragonball_kc_avanzado.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.dragonball_kc_avanzado.data.Repository
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _hero = MutableLiveData<HeroDetail>()
    val hero: LiveData<HeroDetail> get() = _hero

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
}