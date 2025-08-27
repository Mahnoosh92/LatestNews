package com.mahnoosh.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Default())
    val uiState get() = _uiState.asStateFlow()

    fun setUsername(username: String) {
        _uiState.update { state: LoginUiState ->
            when (state) {
                is LoginUiState.Default -> {
                    val isUsernameValid = username.isNotEmpty()
                    val isPasswordValid = state.isPasswordValid
                    val isLoginButtonEnabled = isUsernameValid && (isPasswordValid == true)
                    state.copy(
                        username = username,
                        isUsernameValid = isUsernameValid,
                        isPasswordValid = isPasswordValid,
                        isLoginButtonEnabled = isLoginButtonEnabled,
                        isLoading = false
                    )
                }

                else -> {
                    state
                }
            }
        }
    }

    fun setPassword(password: String) {
        _uiState.update { state: LoginUiState ->
            when (state) {
                is LoginUiState.Default -> {
                    val isUsernameValid = state.isUsernameValid
                    val isPasswordValid = password.isNotEmpty()
                    val isLoginButtonEnabled = isUsernameValid == true && isPasswordValid
                    state.copy(
                        password = password,
                        isUsernameValid = isUsernameValid,
                        isPasswordValid = isPasswordValid,
                        isLoginButtonEnabled = isLoginButtonEnabled,
                        isLoading = false
                    )
                }

                else -> {
                    state
                }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { state: LoginUiState ->
                when (state) {
                    is LoginUiState.Default -> {
                        state.copy(
                            isLoading = true
                        )
                    }

                    else -> {
                        state
                    }
                }
            }
            delay(1500)
            _uiState.value = LoginUiState.Success
        }
    }
}

sealed interface LoginUiState {
    data class Default(
        val username: String = "",
        val password: String = "",
        val isUsernameValid: Boolean? = null,
        val isPasswordValid: Boolean? = null,
        val isLoginButtonEnabled: Boolean = false,
        val isLoading: Boolean = false
    ) : LoginUiState

    object Loading : LoginUiState

    data class Error(val errorMessage: String) : LoginUiState

    object Success: LoginUiState
}