package com.mahnoosh.authentication.register

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object RegisterRoute

fun NavGraphBuilder.registerSection(){
    composable< RegisterRoute>() {
        RegisterScreen()
    }
}

@Composable
fun RegisterScreen() {
    Text(text = "register")
}