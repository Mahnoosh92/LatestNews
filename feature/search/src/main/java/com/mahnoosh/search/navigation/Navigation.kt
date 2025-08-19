package com.mahnoosh.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mahnoosh.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions?) =
    navigate(route = SearchRoute, navOptions)

fun NavGraphBuilder.searchSection(
    onBackPressed: () -> Unit
) {
    composable<SearchRoute>(
        deepLinks = emptyList(),
    ) {
        SearchScreen(onBackPressed = onBackPressed)
    }

}