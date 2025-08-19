package com.mahnoosh.foryou

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mahnoosh.data.model.Headline
import com.mahnoosh.designsystem.DynamicAsyncImage
import com.mahnoosh.designsystem.NewsTextButton
import com.mahnoosh.designsystem.ToggleButton
import com.mahnoosh.designsystem.ui.theme.LatesNewsAppTheme
import com.mahnoosh.foryou.model.Category
import com.mahnoosh.foryou.model.allCategories

@Composable
fun ForYouScreen(viewModel: ForYouViewModel = hiltViewModel(), onNewsClicked: (Headline) -> Unit) {

    var allCategoriesAsState by remember {
        mutableStateOf(allCategories)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        allCategoriesAsState.find { it.isSelected == true }?.let {
            viewModel.getHeadlines(categoryName = it.categoryName)
        }
    }
    ForYouScreen(
        uiState = uiState,
        categories = allCategoriesAsState,
        onCategoryCheckedChanged = { v, c ->
            allCategoriesAsState = allCategoriesAsState.map {
                if (it.categoryName == v)
                    it.copy(isSelected = c)
                else
                    it.copy(isSelected = false)
            }
            viewModel.getHeadlines(categoryName = v)
        },
        onNewsClicked = onNewsClicked
        )
}

@Composable
fun ForYouScreen(
    uiState: ForYouUiState,
    categories: List<Category>,
    onCategoryCheckedChanged: (String, Boolean) -> Unit,
    onNewsClicked: (Headline) -> Unit
) {
    val state = rememberLazyStaggeredGridState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer),
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 24.dp,
            modifier = Modifier
                .testTag("forYou:feed"),
            state = state,
        ) {
            forYouIntroduction(
                onCategoryCheckedChanged = onCategoryCheckedChanged,
                saveFollowedCategories = {}, categories = categories
            )
            when (uiState) {
                is ForYouUiState.Success -> {
                    items(uiState.data) {
                        HeadlineCard(headline = it, onNewsClicked = onNewsClicked)
                    }
                }

                is ForYouUiState.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        }
                    }
                }

                is ForYouUiState.Error -> {
                    item {
                        Text(text = "Something went wrong")
                    }
                }
            }
        }
    }
}

@Composable
fun HeadlineCard(headline: Headline, onNewsClicked: (Headline) -> Unit) {
    val clickActionLabel = stringResource(R.string.feature_foryou_headline_clicked)
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
    Card(
        onClick = {
            headline.url?.toUri()?.let {
                launchCustomChromeTab(context = context, uri = it, toolbarColor = backgroundColor)
            }
//            onNewsClicked.invoke(headline)
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .semantics {
                onClick(label = clickActionLabel, action = null)
            }
            .testTag("newsResourceCard:${headline.id}"),
    ) {
        Column {
            headline.image?.let {
                DynamicAsyncImage(
                    imageUrl = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                )
            }
            Text(
                text = headline.title.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = headline.description.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 4.dp, end = 4.dp, bottom = 12.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
        }
    }
}

fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int) {
    val customTabBarColor = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor).build()
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabBarColor)
        .build()

    customTabsIntent.launchUrl(context, uri)
}

fun LazyStaggeredGridScope.forYouIntroduction(
    categories: List<Category>,
    onCategoryCheckedChanged: (String, Boolean) -> Unit,
    saveFollowedCategories: () -> Unit,
    modifier: Modifier = Modifier,
) {
    item(span = StaggeredGridItemSpan.FullLine, contentType = "forYouIntroduction") {
        Column(modifier = modifier) {
            Text(
                text = stringResource(R.string.feature_foryou_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
            Text(
                text = stringResource(R.string.feature_foryou_onboarding_guidance_subtitle),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp, bottom = 12.dp),
                textAlign = TextAlign.Center
            )
            CategorySelection(
                categories = categories,
                onCategoryCheckedChanged = onCategoryCheckedChanged,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), horizontalArrangement = Arrangement.Center
            ) {
                NewsTextButton(
                    onClick = saveFollowedCategories,
                    enabled = true,
                    content = { Text(text = "Done") })
            }
        }
    }
}

@Composable
fun CategorySelection(
    categories: List<Category>,
    onCategoryCheckedChanged: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyGridState = rememberLazyGridState()
    val categorySelectionTestTag = "forYou:categorySelection"
    Box(modifier = modifier.fillMaxWidth()) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            state = lazyGridState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .heightIn(max = 240.dp)
                .testTag(categorySelectionTestTag)
        ) {
            items(categories) {
                SingleCategory(
                    categoryName = it.categoryName,
                    isSelected = it.isSelected,
                    onClick = onCategoryCheckedChanged
                )
            }
        }
    }
}

@Composable
fun SingleCategory(
    categoryName: String,
    isSelected: Boolean,
    onClick: (String, Boolean) -> Unit,
) {
    Surface(
        modifier = Modifier
            .width(312.dp)
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        selected = isSelected,
        onClick = {
            onClick(categoryName, !isSelected)
        },
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = if (isSelected) Icons.Filled.Info else Icons.Outlined.Info,
                contentDescription = null
            )
            Text(
                text = categoryName,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
            )
            ToggleButton(
                checked = isSelected,
                onCheckedChange = { check -> onClick(categoryName, check) },
                icon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                },
                checkedIcon = {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                }
            )
        }
    }
}

@Composable
fun TopicIcon(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    DynamicAsyncImage(
        placeholder = painterResource(R.drawable.feature_foryou_ic_icon_placeholder),
        imageUrl = imageUrl,
        // decorative
        contentDescription = null,
        modifier = modifier
            .padding(10.dp)
            .size(32.dp),
    )
}

@Preview
@Composable
fun PreviewSingleCategory() {
    LatesNewsAppTheme {
        SingleCategory(categoryName = "General", isSelected = true, onClick = { s, b -> })
    }
}

@Preview
@Composable
fun PreviewCategorySelection() {
    LatesNewsAppTheme {
        CategorySelection(onCategoryCheckedChanged = { c, i -> }, categories = allCategories)
    }
}