package com.mahnoosh.foryou.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable


@Serializable data object ForYouRoute // route to ForYou screen

@Serializable data object ForYouBaseRoute

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(route = ForYouRoute, navOptions)

fun NavGraphBuilder.forYouSection(
) {
    navigation<ForYouBaseRoute>(startDestination = ForYouRoute) {
        composable<ForYouRoute>(
            deepLinks = emptyList(),
        ) {
            Text("Foryoy")
        }
    }
}