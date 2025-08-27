package com.mahnoosh.authentication.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Serializable
data object LoginRoute

fun NavGraphBuilder.loginSection(onSuccess: () -> Unit) {
    composable<LoginRoute>() {
        LoginScreen(onSuccess = onSuccess)
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    Login(
        uiState = uiState,
        onUsernameChanged = { loginViewModel.setUsername(it) },
        onPasswordChanged = { loginViewModel.setPassword(it) },
        login = { loginViewModel.login() }, onSuccess = onSuccess
    )
}

@Composable
fun Login(
    uiState: LoginUiState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    login: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is LoginUiState.Default -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = uiState.username,
                        onValueChange = { onUsernameChanged(it) },
                        label = { Text(text = "Username") },
                        isError = uiState.isUsernameValid != true && uiState.isPasswordValid != null,
                        supportingText = {
                            if (uiState.isUsernameValid == false) {
                                Text(
                                    text = "Username is not valid",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = { onPasswordChanged(it) },
                        label = { Text("Password") },
                        isError = uiState.isPasswordValid != true && uiState.isPasswordValid != null,
                        supportingText = {
                            if (uiState.isPasswordValid == false) {
                                Text(
                                    text = "Password is not valid",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    // Login Button
                    Button(
                        onClick = { login.invoke() },
                        enabled = uiState.isLoginButtonEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (!uiState.isLoading)
                            Text("Login")
                        else
                            CircularProgressIndicator(
                                modifier = Modifier.size(10.dp),
                                color = Color.White
                            )
                    }

                }
            }
        }

        LoginUiState.Success -> onSuccess()
        else -> {}
    }
}