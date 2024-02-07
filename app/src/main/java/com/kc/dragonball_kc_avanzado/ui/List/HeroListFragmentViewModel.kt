package com.kc.dragonball_kc_avanzado.ui.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.dragonball_kc_avanzado.data.Repository
import com.kc.dragonball_kc_avanzado.domain.model.HeroList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroListFragmentViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    sealed class State {
        data object Idle : State()
        class Error(val errorMessage: String) : State()
        data object Loading : State()
        class HeroesLoaded(val heroes: List<HeroList>) : State()
        class HeroSelected(val hero: HeroList) : State()
        class HeroUpdated(val hero: HeroList) : State()
    }

    fun getHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = State.Loading
            val heroes = withContext(Dispatchers.IO) {
                repository.getHeroes()
            }
            _state.value = State.HeroesLoaded(heroes)
        }
    }

    fun removeLocalToken() {
        viewModelScope.launch { repository.deleteToken() }
    }

    fun isPersistentSession() = repository.isPersistentSession()
}