package com.mahnoosh.detail.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mahnoosh.designsystem.DynamicAsyncImage
import com.mahnoosh.detail.DetailViewModel
import kotlinx.serialization.Serializable


@Serializable
data class DetailRoute(val headlineId: String)

fun NavGraphBuilder.detailNavGraph(onBackPressed: () -> Unit) {
    composable<DetailRoute> { entry ->
        val id = entry.toRoute<DetailRoute>().headlineId
        DetailScreen(viewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(key = id) { factory ->
            factory.create(id)
        }, onBackPressed = onBackPressed)
    }
}

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val headline by viewModel.headLine.collectAsStateWithLifecycle()

    Box(modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
            headline?.let {
                Column(modifier = Modifier.fillMaxWidth()) {
                    DynamicAsyncImage(
                        imageUrl = headline?.image!!,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(300.dp)
                    )
                    Text(
                        text = headline?.description!!,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}