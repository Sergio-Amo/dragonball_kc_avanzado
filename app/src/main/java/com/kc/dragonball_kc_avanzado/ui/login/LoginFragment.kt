package com.kc.dragonball_kc_avanzado.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kc.dragonball_kc_avanzado.R
import com.kc.dragonball_kc_avanzado.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialState()
        setObservers()
        setListeners()
    }

    private fun setInitialState() {
        with(binding){
            // TODO: Load username from shared preferences (or use encrypted and use token instead)
            // Force validation of fields to set the proper button enable state
            viewModel.onEmailChanged(editTextTextEmailAddress.text.toString())
            viewModel.onPasswordChanged(editTextTextPassword.text.toString())
        }
    }

    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect { state ->
                when (state) {
                    is LoginViewModel.State.Idle -> idle()
                    is LoginViewModel.State.FieldsValidated -> setLoginButtonState(state.valid)
                    is LoginViewModel.State.Error -> showError(state.errorMessage)
                    is LoginViewModel.State.Loading -> showLoading(true)
                    is LoginViewModel.State.SuccessLogin -> successLogin(state.token)
                }
            }
        }
    }

    private fun idle() {
        showLoading(false)
    }

    private fun setLoginButtonState(enable: Boolean) {
        binding.loginButton.isEnabled = enable
    }

    private fun showError(error: String) {
        showLoading(false)
        Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        binding.loadingSpinner.root.isVisible = show
    }

    private fun successLogin(token: String) {
        // TODO: Save token or user password (if encrypted), only user if not
        //  Navigate to Home fragment
    }

    private fun setListeners() {
        with (binding) {
            // Login button click
            loginButton.setOnClickListener {
                val email = editTextTextEmailAddress.text.toString()
                val password = editTextTextPassword.text.toString()
                //viewModel.doLogin(email, password)
            }
            //EditText text changed listeners
            editTextTextEmailAddress.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
            editTextTextPassword.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
            // Remember user checkbox
            /*rememberCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) LoginRepository.removeEmail(this)
                LoginRepository.saveCheckBoxState(this, isChecked)
            }*/
        }
    }


}