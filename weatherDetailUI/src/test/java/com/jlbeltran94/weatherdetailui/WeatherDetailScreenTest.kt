package com.jlbeltran94.weatherdetailui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.commonui.TestTags
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `weather Detail Screen- Loading State`() {
        composeTestRule.setContent {
            WeatherDetailScreen(
                onNavigateBack = {},
                onNavigateToError = { _ -> },
                uiState = WeatherDetailUiState.Loading
            )
        }
        composeTestRule.onNodeWithTag(TestTags.WEATHER_DETAIL_SHIMMER).assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun `weather Detail Screen- Error State`() {
        var errorType: ErrorType? = null
        composeTestRule.setContent {
            WeatherDetailScreen(
                onNavigateBack = {},
                onNavigateToError = { type ->
                    errorType = type
                },
                uiState = WeatherDetailUiState.Error(ErrorType.IO_ERROR)
            )
        }
        assertEquals(ErrorType.IO_ERROR, errorType)
    }

    @Test
    fun `weather Detail Screen - Success State`() {
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
        composeTestRule.setContent {
            WeatherDetailScreen(
                onNavigateBack = {},
                onNavigateToError = { _ -> },
                uiState = WeatherDetailUiState.Success(weather)
            )
        }
        composeTestRule.onNodeWithTag(TestTags.WEATHER_DETAIL_CONTENT).assertExists()
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            "${weather.cityName}, ${weather.country}",
            useUnmergedTree = true
        ).onFirst().assertExists().assertIsDisplayed()
    }
}
