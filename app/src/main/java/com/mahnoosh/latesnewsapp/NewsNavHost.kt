package com.mahnoosh.latesnewsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mahnoosh.bookmark.navigation.bookmarksScreen
import com.mahnoosh.data.model.Headline
import com.mahnoosh.foryou.SingleNews
import com.mahnoosh.foryou.navigateToTopic
import com.mahnoosh.foryou.navigation.ForYouBaseRoute
import com.mahnoosh.foryou.navigation.forYouSection
import com.mahnoosh.interest.navigation.interestScreen
import com.mahnoosh.search.navigation.searchSection

@Composable
fun NewsNavHost(
    appState: NewsAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = ForYouBaseRoute,
        modifier = modifier,
    ) {
        forYouSection(
            onNewsClicked = { headline: Headline ->
                appState.navController.navigateToTopic(topicId = headline.id)
            },
            newsDestination = { SingleNews(onBackPressed = appState.navController::popBackStack) }
        )
        bookmarksScreen()
        interestScreen()
        searchSection(onBackPressed = appState.navController::popBackStack)
    }

}