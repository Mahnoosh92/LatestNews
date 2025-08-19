package com.mahnoosh.foryou

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class NewsRoute(val id: String)

fun NavController.navigateToTopic(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = NewsRoute(topicId)) {
        navOptions()
    }
}

fun NavGraphBuilder.SingleNews(onBackPressed: () -> Unit) {
    composable<NewsRoute> { entry ->
        val id = entry.toRoute<NewsRoute>().id
        SingleNewsScreen(
            onBackPressed = onBackPressed,
            viewModel = hiltViewModel<SingleNewsViewModel, SingleNewsViewModel.Factory>(
                key = id,
            ) { factory ->
                factory.create(id)
            })
    }
}

@Composable
fun SingleNewsScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SingleNewsViewModel = hiltViewModel()
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        Text(text = viewModel.topicId)
    }
}