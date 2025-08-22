package com.mahnoosh.latesnewsapp.navigation

import com.mahnoosh.authentication.navigation.AuthRoute
import com.mahnoosh.dashboard.navigation.DashboardRoute
import com.mahnoosh.detail.navigation.DetailRoute
import kotlin.reflect.KClass

enum class AppTopLevelDestination(
    val baseRoute: KClass<*>,
) {
    AUTH(baseRoute = AuthRoute::class),
    DASHBOARD(baseRoute = DashboardRoute::class),
    DETAIL(baseRoute = DetailRoute::class)
}