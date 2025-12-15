package com.jlbeltran94.searchlocationui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.commonui.TestTags
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationui.screen.SearchScreen
import com.jlbeltran94.searchlocationui.screen.SearchUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `search screen test - idle state no recent searches`() {
        composeTestRule.setContent {
            SearchScreen(
                onNavigateToDetail = { },
                onNavigateToError = { },
                onClearSearch = { },
                onSearchQueryChange = { },
                uiState = SearchUiState.Idle,
                searchQuery = "",
                recentSearches = emptyList(),
                eventFlow = MutableSharedFlow()
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.RECENT_SEARCH_ITEM).assertDoesNotExist()
    }

    @Test
    fun `search screen test - idle state with recent searches`() {
        composeTestRule.setContent {
            SearchScreen(
                onNavigateToDetail = { },
                onNavigateToError = { },
                onClearSearch = { },
                onSearchQueryChange = { },
                uiState = SearchUiState.Idle,
                searchQuery = "",
                recentSearches = listOf(
                    RecentSearch(
                        id = "id",
                        cityName = "cityName",
                        region = "region",
                        country = "country",
                        temperature = 0.0,
                        condition = "condition",
                        conditionIcon = "conditionIcon",
                        timestamp = 0
                    )
                ),
                eventFlow = MutableSharedFlow()
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(TestTags.RECENT_SEARCH_ITEM).onFirst().assertIsDisplayed()
    }

    @Test
    fun `search screen test - Loading state`() {
        composeTestRule.setContent {
            SearchScreen(
                onNavigateToDetail = { },
                onNavigateToError = { },
                onClearSearch = { },
                onSearchQueryChange = { },
                uiState = SearchUiState.Loading,
                searchQuery = "",
                recentSearches = emptyList(),
                eventFlow = MutableSharedFlow()
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.SEARCH_SHIMMER).assertIsDisplayed()
    }

    @Test
    fun `search screen test - No results state`() {
        composeTestRule.setContent {
            SearchScreen(
                onNavigateToDetail = { },
                onNavigateToError = { },
                onClearSearch = { },
                onSearchQueryChange = { },
                uiState = SearchUiState.NoResults,
                searchQuery = "",
                recentSearches = emptyList(),
                eventFlow = MutableSharedFlow()
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NO_RESULTS_FOUND).assertIsDisplayed()
    }

    @Test
    fun `search screen test - Success state`() {
        composeTestRule.setContent {
            SearchScreen(
                onNavigateToDetail = { },
                onNavigateToError = { },
                onClearSearch = { },
                onSearchQueryChange = { },
                uiState = SearchUiState.Success(
                    listOf(
                        City(
                            id = "id",
                            name = "name",
                            region = "region",
                            country = "country",
                            lat = 0.0,
                            lon = 0.0
                        )
                    )
                ),
                searchQuery = "",
                recentSearches = emptyList(),
                eventFlow = MutableSharedFlow()
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(TestTags.CITY_ITEM).assertCountEquals(1).onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun `search screen test - Error state`() {
        var errorType: ErrorType? = null
        composeTestRule.setContent {
            SearchScreen(
                onNavigateToDetail = { },
                onNavigateToError = { errorType = it },
                onClearSearch = { },
                onSearchQueryChange = { },
                uiState = SearchUiState.Error(errorType = ErrorType.IO_ERROR),
                searchQuery = "",
                recentSearches = emptyList(),
                eventFlow = MutableSharedFlow()
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).assertIsDisplayed()
        assertEquals(ErrorType.IO_ERROR, errorType)
    }
}
