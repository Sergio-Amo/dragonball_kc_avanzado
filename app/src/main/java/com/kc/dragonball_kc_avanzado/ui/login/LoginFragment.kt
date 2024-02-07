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
import androidx.navigation.fragment.findNavController
import com.kc.dragonball_kc_avanzado.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        // Check if there's a session token saved
        lifecycleScope.launch { viewModel.isSavedSession() }
    }

    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect { state ->
                when (state) {
                    is LoginViewModel.State.Idle -> idle()
                    is LoginViewModel.State.FieldsValidated -> setLoginButtonState(state.valid)
                    is LoginViewModel.State.Error -> showError(state.errorMessage)
                    is LoginViewModel.State.Loading -> showLoading(true)
                    is LoginViewModel.State.SuccessLogin -> successLogin(state.saved)
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

    private fun successLogin(saved: Boolean) {
        // Set remember session checkmark as checked
        // (I'll permit going back and uncheck to clear the token)
        binding.rememberCheckBox.isChecked = saved
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHeroListFragment())
    }

    private fun setListeners() {
        with(binding) {
            // Login button click
            loginButton.setOnClickListener {
                viewModel.loginPressed(
                    editTextTextEmailAddress.text.toString(),
                    editTextTextPassword.text.toString(),
                    rememberCheckBox.isChecked,
                )
            }
            //EditText text changed listeners
            editTextTextEmailAddress.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
            editTextTextPassword.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
            // Listen for checkBox checked state
            rememberCheckBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.checkBoxStateChanged(isChecked)
            }
        }
    }
}