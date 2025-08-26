package com.mahnoosh.authentication.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mahnoosh.authentication.R

@Serializable
data object SplashRoute

fun NavGraphBuilder.splashSection(
    onNavigateToLogin: () -> Unit,
    onNavigateToOnBoarding: () -> Unit
) {
    composable<SplashRoute>() {
        SplashScreen(onNavigateToLogin = onNavigateToLogin, onNavigateToOnBoarding = onNavigateToOnBoarding)
    }
}

@Composable
fun SplashScreen(
    vm: SplashViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToOnBoarding: () -> Unit
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.NavigateToLogin -> onNavigateToLogin()
            is UiState.NavigateToOnBoarding -> onNavigateToOnBoarding()
            else -> {}
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )
    }
}