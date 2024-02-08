package com.kc.dragonball_kc_avanzado.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.dragonball_kc_avanzado.data.Repository
import com.kc.dragonball_kc_avanzado.utils.FieldValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val fieldValidator: FieldValidator,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private var isValidEmail = false
    private var isValidPassword = false
    private var checkBoxChecked = false

    sealed class State {
        data object Idle : State()
        class FieldsValidated(val valid: Boolean) : State()
        class Error(val errorMessage: String) : State()
        data object Loading : State()
        class SuccessLogin(val saved: Boolean = false) : State()
    }


    suspend fun isSavedSession(): Boolean {
        val sessionSaved: Boolean = withContext(Dispatchers.IO) {
            !repository.getToken().isNullOrEmpty()
        }
        if (sessionSaved) _state.value = State.SuccessLogin(true)
        return sessionSaved
    }

    fun onEmailChanged(email: String) {
        fieldValidator.validateEmail(email).apply {
            if (this != isValidEmail) {
                isValidEmail = this
                validateFields()
            }
        }
    }

    fun onPasswordChanged(password: String) {
        fieldValidator.validatePassword(password).apply {
            if (this != isValidPassword) {
                isValidPassword = this
                validateFields()
            }
        }
    }

    /** Check if email and password are valid and set the State.FieldsValidated value accordingly. **/
    private fun validateFields() {
        _state.value = State.FieldsValidated(isValidEmail && isValidPassword)
    }

    fun loginPressed(email: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            if (repository.login(email, password, remember))
                _state.value = State.SuccessLogin(remember)
        }
    }

    fun checkBoxStateChanged(checked: Boolean) {
        checkBoxChecked = checked
        if (!checked) {
            viewModelScope.launch { repository.deleteToken() }
        }
    }

}