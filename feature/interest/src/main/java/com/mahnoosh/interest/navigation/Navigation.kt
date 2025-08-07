package com.mahnoosh.interest.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data class InterestsRoute(
    // The ID of the topic which will be initially selected at this destination
    val initialTopicId: String? = null,
)

fun NavController.navigateToInterests(
    initialTopicId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = InterestsRoute(initialTopicId), navOptions)
}
fun NavGraphBuilder.interestScreen(
) {
    composable<InterestsRoute> {
        Text("Interests")
    }
}
