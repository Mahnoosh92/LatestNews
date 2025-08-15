package com.mahnoosh.foryou.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable data class NewsRoute(val id: String)

fun NavController.navigateToTopic(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = NewsRoute(topicId)) {
        navOptions()
    }
}

fun NavGraphBuilder.SingleNews(
) {
    composable<NewsRoute> { entry ->
        val id = entry.toRoute<NewsRoute>().id
        SingleNewsScreen()
    }
}

@Composable
fun SingleNewsScreen() {

}