package com.mahnoosh.latesnewsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.mahnoosh.authentication.navigation.AuthRoute
import com.mahnoosh.authentication.navigation.authNavGraph
import com.mahnoosh.authentication.navigation.navigateToLogin
import com.mahnoosh.authentication.navigation.navigateToOnBoarding
import com.mahnoosh.dashboard.navigation.DashboardRoute
import com.mahnoosh.dashboard.navigation.dashboardNavGraph
import com.mahnoosh.detail.navigation.DetailRoute
import com.mahnoosh.detail.navigation.detailNavGraph
import com.mahnoosh.latesnewsapp.navigation.AppTopLevelDestination

@Composable
fun NewsNavHost(
    appState: NewsAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = AppTopLevelDestination.AUTH.baseRoute,
        modifier = modifier,
    ) {
        authNavGraph(onNavigateToLogin = {
            appState.navController.navigateToLogin()
        }, onNavigateToOnBoarding = {
            appState.navController.navigateToOnBoarding()
        }, onBoardingCompleted = {
            appState.navController.navigateToLogin()
        })
        dashboardNavGraph()
        detailNavGraph()
    }

}

// Navigation between graphs

fun NavController.navigateToDashboardGraph() = navigate(DashboardRoute) {
    popUpTo(AuthRoute) { inclusive = true }
}

fun NavController.navigateToDetailGraph() = navigate(DetailRoute)