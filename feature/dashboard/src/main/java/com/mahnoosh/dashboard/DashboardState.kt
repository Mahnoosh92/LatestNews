package com.mahnoosh.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.mahnoosh.bookmark.navigation.navigateToBookmarks
import com.mahnoosh.dashboard.navigation.DashboardTopLevelDestination
import com.mahnoosh.foryou.navigation.navigateToForYou
import com.mahnoosh.interest.navigation.navigateToInterests
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberDashboardState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) = remember(coroutineScope, navController) {
    DashboardState(navController = navController, coroutineScope = coroutineScope)
}

@Stable
class DashboardState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: DashboardTopLevelDestination?
        @Composable get() {
            return DashboardTopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    fun navigateToDashboardTopLevelDestination(topLevelDestination: DashboardTopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            DashboardTopLevelDestination.FOR_YOU -> navController.navigateToForYou(
                topLevelNavOptions
            )

            DashboardTopLevelDestination.BOOKMARKS -> navController.navigateToBookmarks(
                topLevelNavOptions
            )

            DashboardTopLevelDestination.INTERESTS -> navController.navigateToInterests(
                null,
                topLevelNavOptions
            )
        }
    }
}

