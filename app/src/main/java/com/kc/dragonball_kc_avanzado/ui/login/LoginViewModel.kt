package com.kc.dragonball_kc_avanzado.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kc.dragonball_kc_avanzado.data.local.EncryptedPreferences
import com.kc.dragonball_kc_avanzado.utils.Validator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val sharedPreferences: EncryptedPreferences) :
    ViewModel() {

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
        data object SuccessLogin : State()
    }


    fun checkForSavedSession(): Boolean {
        if (sharedPreferences.getToken().isNullOrEmpty()) return false
        _state.value = State.SuccessLogin
        return true
    }

    fun onEmailChanged(email: String) {
        Validator.validateEmail(email).apply {
            if (this != isValidEmail) {
                isValidEmail = this
                validateFields()
            }
        }
    }

    fun onPasswordChanged(password: String) {
        Validator.validatePassword(password).apply {
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

    fun loginPressed(email: String, password: String) {
        //TODO: Do login && if checkBoxChecked saveToken
        // TODO: REMOVE DUMMY TEST
        sharedPreferences.saveToken("LoremIpsumSitAmet")
    }

    fun checkBoxStateChanged(checked: Boolean) {
        checkBoxChecked = checked
    }

}