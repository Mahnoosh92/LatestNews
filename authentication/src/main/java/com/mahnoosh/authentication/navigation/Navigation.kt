package com.mahnoosh.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.mahnoosh.authentication.login.LoginRoute
import com.mahnoosh.authentication.login.loginSection
import com.mahnoosh.authentication.register.RegisterRoute
import com.mahnoosh.authentication.register.registerSection
import com.mahnoosh.authentication.splash.SplashRoute
import com.mahnoosh.authentication.splash.splashSection
import kotlinx.serialization.Serializable

@Serializable data object AuthRoute
fun NavController.navigateToLogin() = navigate(route = LoginRoute)
fun NavController.navigateToRegister() = navigate(route = RegisterRoute)

fun NavGraphBuilder.authNavGraph(onNavigateToLogin: () -> Unit) {
    navigation<AuthRoute>(startDestination = SplashRoute){
        splashSection(onNavigateToLogin = onNavigateToLogin)
        loginSection()
        registerSection()
    }
}