package com.mahnoosh.detail.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable data object DetailRoute

fun NavGraphBuilder.detailNavGraph() {
    composable<DetailRoute> {
        DetailScreen()
    }
}

@Composable
fun DetailScreen() {

}