package com.jlbeltran94.weatherapp.presentation.detail

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SearchCitiesUseCase
import com.jlbeltran94.weatherapp.MainActivity
import com.jlbeltran94.commonui.TestTags
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailcomponent.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class WeatherDetailScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var searchCitiesUseCase: SearchCitiesUseCase

    @Inject
    lateinit var getWeatherUseCase: GetWeatherUseCase

    @Inject
    lateinit var getRecentSearchesUseCase: GetRecentSearchesUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
        every { getRecentSearchesUseCase() } returns flowOf(emptyList())
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun detailScreen_displaysCorrectData_afterNavigationFromSearch() {
        // GIVEN
        // 1. Mock search results
        val searchQuery = "London"
        val city = City(
            id = "1",
            name = "London",
            region = "City of London, Greater London",
            country = "United Kingdom",
            lat = 51.52,
            lon = -0.11
        )
        coEvery { searchCitiesUseCase(searchQuery) } returns Result.success(listOf(city))

        // 2. Mock weather detail results
        val weather = Weather(
            cityId = "1",
            cityName = "London",
            region = "City of London, Greater London",
            country = "United Kingdom",
            temperature = 15.0,
            condition = "Sunny",
            conditionIcon = "",
            highTemp = 20.0,
            lowTemp = 10.0,
            feelsLike = 14.0,
            humidity = 50,
            windSpeed = 5.0,
            hourlyForecast = emptyList(),
            dailyForecast = emptyList(),
            cityLat = 51.52,
            cityLon = -0.11
        )
        // The detail screen uses the city name and country for its query
        val detailQuery = "London,United Kingdom"
        coEvery { getWeatherUseCase(detailQuery) } returns Result.success(weather)

        // WHEN
        // 1. Perform search
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.SEARCH_INPUT), 5000)
        composeTestRule.onNodeWithTag(TestTags.SEARCH_INPUT).performTextInput(searchQuery)
        composeTestRule.waitUntilNodeCount(hasTestTag(TestTags.CITY_ITEM), 1, 5000)

        // 2. Click on the result to navigate
        composeTestRule.onNodeWithTag(TestTags.CITY_ITEM).performClick()

        // THEN
        // 1. Verify that the correct details are displayed on the detail screen
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(TestTags.DETAIL_TEMPERATURE), 5000)
        composeTestRule.onNodeWithTag(TestTags.DETAIL_TEMPERATURE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.DETAIL_CONDITION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.DETAIL_HIGH_LOW).assertIsDisplayed()
    }
}
