package com.mahnoosh.dashboard.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mahnoosh.bookmark.navigation.bookmarksScreen
import com.mahnoosh.dashboard.rememberDashboardState
import com.mahnoosh.foryou.navigation.ForYouBaseRoute
import com.mahnoosh.foryou.navigation.ForYouRoute
import com.mahnoosh.foryou.navigation.forYouSection
import com.mahnoosh.interest.navigation.interestScreen
import kotlinx.serialization.Serializable

@Serializable
data object DashboardRoute

@Serializable
data object DashboardScreen

fun NavGraphBuilder.dashboardNavGraph() {
    navigation<DashboardRoute>(startDestination = DashboardScreen) {
        composable<DashboardScreen>() {
            DashboardScreen()
        }
    }
}

@Composable
fun DashboardScreen() {
    val dasboardState = rememberDashboardState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentDestination = dasboardState.currentTopLevelDestination,
                onDashboardBottomNavigationItemClicked = {
                    dasboardState.navigateToDashboardTopLevelDestination(it)
                })
        }
    ) { padding ->
        NavHost(
            navController = dasboardState.navController,
            startDestination = ForYouBaseRoute,
            modifier = Modifier.padding(padding)
        ) {
            forYouSection { }
            bookmarksScreen()
            interestScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentDestination: DashboardTopLevelDestination?,
    onDashboardBottomNavigationItemClicked: (DashboardTopLevelDestination) -> Unit
) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        DashboardTopLevelDestination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = currentDestination?.route == destination.route,
                onClick = {
                    onDashboardBottomNavigationItemClicked(destination)
                },
                icon = {
                    Icon(imageVector = destination.selectedIcon, contentDescription = null)
                },
                label = { Text(text = stringResource(destination.iconTextId)) }
            )
        }
    }
}