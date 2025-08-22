package com.mahnoosh.dashboard.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.mahnoosh.bookmark.navigation.BookmarksRoute
import com.mahnoosh.dashboard.R
import com.mahnoosh.foryou.navigation.ForYouBaseRoute
import com.mahnoosh.foryou.navigation.ForYouRoute
import com.mahnoosh.interest.navigation.InterestsRoute
import kotlin.reflect.KClass
import com.mahnoosh.bookmark.R as bookmarkR
import com.mahnoosh.foryou.R as foryouR
import com.mahnoosh.interest.R as interestR

enum class DashboardTopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    FOR_YOU(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = foryouR.string.feature_foryou_title,
        titleTextId = R.string.app_name,
        route = ForYouRoute::class,
        baseRoute = ForYouBaseRoute::class,
    ),
    BOOKMARKS(
        selectedIcon = Icons.Filled.Face,
        unselectedIcon = Icons.Outlined.Face,
        iconTextId = bookmarkR.string.feature_bookmarks_title,
        titleTextId = R.string.app_name,
        route = BookmarksRoute::class,
    ),
    INTERESTS(
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.Favorite,
        iconTextId = interestR.string.feature_search_interests,
        titleTextId = R.string.app_name,
        route = InterestsRoute::class,
    ),
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
