package com.jlbeltran94.weatherapp.presentation.search

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import com.jlbeltran94.commonmodel.exception.DomainException
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SearchCitiesUseCase
import com.jlbeltran94.weatherapp.MainActivity
import com.jlbeltran94.commonui.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
class SearchScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var searchCitiesUseCase: SearchCitiesUseCase

    @Inject
    lateinit var getRecentSearchesUseCase: GetRecentSearchesUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
        // Provide a default answer for the recent searches use case, which is called on init
        every { getRecentSearchesUseCase() } returns flowOf(emptyList())
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchScreen_displaysResults_onSuccessfulSearch() {
        // Given
        val searchQuery = "London"
        val cities = listOf(
            City(
                id = "1",
                name = "London",
                region = "City of London, Greater London",
                country = "United Kingdom",
                lat = 51.52,
                lon = -0.11
            ),
        )
        coEvery { searchCitiesUseCase(searchQuery) } returns Result.success(cities)

        // Wait for the search input to be ready
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.SEARCH_INPUT), 3000)

        // When
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).performTextInput(searchQuery)

        // Then: Verify that the city item is displayed
        composeTestRule.waitUntilNodeCount(hasTestTag(TestTags.CITY_ITEM), 1, 5000)
        composeTestRule.onNodeWithTag(TestTags.CITY_ITEM).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchScreen_displaysNoResults_onEmptySearch() {
        // Given
        val searchQuery = "NotACity"
        coEvery { searchCitiesUseCase(searchQuery) } returns Result.success(emptyList())

        // Wait for the search input to be ready
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.SEARCH_INPUT), 3000)

        // When
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).performTextInput(searchQuery)

        // Then: Verify that the "No Results" message is displayed
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.NO_RESULTS_FOUND), 5000)
        composeTestRule.onNodeWithTag(TestTags.NO_RESULTS_FOUND).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchScreen_navigatesToError_onSearchFailure() {
        // Given
        val searchQuery = "FailingCity"
        // Use IOException to simulate a network error
        coEvery { searchCitiesUseCase(searchQuery) } returns Result.failure(
            DomainException.IOError(
                IOException("Network Error")
            )
        )

        // Wait for the search input to be ready
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.SEARCH_INPUT), 3000)

        // When
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).performTextInput(searchQuery)

        // Then: Verify that the app navigates to the error screen
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.NO_NETWORK_SCREEN), 5000)
        composeTestRule.onNodeWithTag(TestTags.NO_NETWORK_SCREEN).assertIsDisplayed()
    }
}
