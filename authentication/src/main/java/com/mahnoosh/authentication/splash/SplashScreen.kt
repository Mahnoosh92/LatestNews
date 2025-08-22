package com.mahnoosh.authentication.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
@Serializable
data object SplashRoute

fun NavGraphBuilder.splashSection(onNavigateToLogin: () -> Unit) {
    composable<SplashRoute>() {
        SplashScreen(onNavigateToLogin = onNavigateToLogin)
    }
}

@Composable
fun SplashScreen(
    vm: SplashViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Success -> onNavigateToLogin()
            else -> {}
        }
    }
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "News", style = MaterialTheme.typography.titleLarge)
    }
}