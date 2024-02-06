package com.kc.dragonball_kc_avanzado.ui.login

import androidx.lifecycle.ViewModel
import com.kc.dragonball_kc_avanzado.utils.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private var isValidEmail = false
    private var isValidPassword = false

    sealed class State {
        data object Idle : State()
        class FieldsValidated(val valid: Boolean) : State()
        class Error(val errorMessage: String) : State()
        data object Loading : State()
        class SuccessLogin(val token: String) : State()
    }

    fun onEmailChanged(email: String) {
        Validator.validateEmail(email).apply {
            if (this != isValidEmail) {
                isValidEmail = this
                setFieldStatus()
            }
        }
    }

    fun onPasswordChanged(password: String) {
        Validator.validatePassword(password).apply {
            if (this != isValidPassword) {
                isValidPassword = this
                setFieldStatus()
            }
        }
    }

    /** Check if email and password are valid and set the State.FieldsValidated value accordingly. **/
    private fun setFieldStatus() {
        _state.value = State.FieldsValidated(isValidEmail && isValidPassword)
    }

}