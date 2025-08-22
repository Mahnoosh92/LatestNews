package com.mahnoosh.latesnewsapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    appState: NewsAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        NewsNavHost(
            appState = appState,
            onShowSnackbar = { message, action ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = SnackbarDuration.Short,
                ) == ActionPerformed
            },
        )
    }
}