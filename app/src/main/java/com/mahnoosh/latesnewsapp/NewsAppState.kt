package com.mahnoosh.latesnewsapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mahnoosh.latesnewsapp.navigation.AppTopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberNewsAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) = remember(coroutineScope, navController) {
    NewsAppState(navController = navController, coroutineScope = coroutineScope)
}

@Stable
class NewsAppState(
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

    val currentTopLevelDestination: AppTopLevelDestination?
        @Composable get() {
            return AppTopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.baseRoute) == true
            }
        }
}