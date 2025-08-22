package com.mahnoosh.authentication.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object LoginRoute

fun NavGraphBuilder.loginSection(){
    composable<LoginRoute>() {
        LoginScreen()
    }
}

@Composable
fun LoginScreen() {
    Text(text = "login")
}