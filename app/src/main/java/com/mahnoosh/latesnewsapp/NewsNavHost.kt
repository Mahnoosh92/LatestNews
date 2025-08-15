package com.mahnoosh.latesnewsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mahnoosh.bookmark.navigation.bookmarksScreen
import com.mahnoosh.foryou.navigation.ForYouBaseRoute
import com.mahnoosh.foryou.navigation.SingleNews
import com.mahnoosh.foryou.navigation.SingleNewsScreen
import com.mahnoosh.foryou.navigation.forYouSection
import com.mahnoosh.interest.navigation.interestScreen

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
        forYouSection(){
            SingleNews()
        }
        bookmarksScreen()
        interestScreen()
    }

}