package com.mahnoosh.foryou

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.mahnoosh.foryou.model.allCategories
import org.junit.Rule
import org.junit.Test


class ForYouScreenKtTest {

    @get:Rule(order = 0)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verifyForYouIntroductionElementsAreShown() {
        composeTestRule.setContent {
            Box {
                ForYouScreen(
                    uiState = ForYouUiState.Loading,
                    categories = allCategories,
                    onCategoryCheckedChanged = { _, _ -> },
                    onNewsClicked = {})
            }
        }

        composeTestRule
            .onNodeWithTag("forYou:feed")
            .assertExists()
        composeTestRule
            .onNodeWithContentDescription("Loader")
            .assertExists()
    }

    @Test
    fun verifyFirstCategoryIsSelectedOthersAreDeselected() {
        composeTestRule.setContent {
            Box {
                ForYouScreen(
                    uiState = ForYouUiState.Loading,
                    categories = allCategories,
                    onCategoryCheckedChanged = { _, _ -> },
                    onNewsClicked = {})
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag("toggle-button") and hasContentDescription("SingleCategory${allCategories[0].categoryName}"),
                useUnmergedTree = true
            )
            .assertIsOn()

        // Assert that all other toggle buttons are OFF
        for (i in 1 until allCategories.size) {
            composeTestRule
                .onNode(hasTestTag("forYou:categorySelection"))
                .performScrollToNode(
                    matcher = hasContentDescription("SingleCategory${allCategories[i].categoryName}")
                )

            // Now, assert that the 10th category's toggle is OFF
            composeTestRule
                .onNode(
                    matcher = hasTestTag("toggle-button") and hasContentDescription("SingleCategory${allCategories[i].categoryName}")
                )
                .assertIsOff()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyClickedCategoryIsSelectedOthersAreDeselected() {
        var allCategoriesAsState by mutableStateOf(allCategories)

        composeTestRule.setContent {
            Box {
                ForYouScreen(
                    uiState = ForYouUiState.Loading,
                    categories = allCategoriesAsState,
                    onCategoryCheckedChanged = { v, c ->

                        allCategoriesAsState = allCategoriesAsState.map {
                            if (it.categoryName == v)
                                it.copy(isSelected = c)
                            else
                                it.copy(isSelected = false)
                        }
                    },
                    onNewsClicked = {})
            }
        }

        val targetCategoryName = allCategories[3].categoryName
        val targetNode = composeTestRule.onNode(
            hasContentDescription("SingleCategory$targetCategoryName")
        )

        // Find the LazyRow and scroll to the node.
        composeTestRule
            .onNode(hasTestTag("forYou:categorySelection"))
            .performScrollToNode(hasContentDescription("SingleCategory${allCategories[3].categoryName}"))

        // Now, wait until the node you want to click exists.
        // This is a more explicit and robust way to ensure the node is ready for interaction.
        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription("SingleCategory${allCategories[3].categoryName}")
        )
        // Now you can safely perform the click.
        composeTestRule
            .onNode(hasContentDescription("SingleCategory${allCategories[3].categoryName}"))
            .performClick()

        // Now, perform your assertion. The UI should be in the correct state.
        targetNode.assertIsOn()

        allCategories.forEach { category->
            if(category.categoryName !=allCategories[3].categoryName){
                composeTestRule
                    .onNode(
                        matcher = hasTestTag("toggle-button") and hasContentDescription("SingleCategory${category.categoryName}"),
                        useUnmergedTree = true
                    )
                    .assertIsOff()

            }

        }
    }
}