package com.jlbeltran94.weatherapp.presentation.splash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.screens.splash.SplashScreen
import com.jlbeltran94.weatherapp.presentation.screens.splash.SplashUiState
import com.jlbeltran94.commonui.TestTags
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test Splash screen - Loading status`() {
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashUiState.Loading,
                onNavigateToSearch = { },
                onNavigateToError = { }
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SPLASH_LOADING).assertExists().assertIsDisplayed()
    }

    @Test
    fun `test Splash screen - Success status`() {
        var navigateToSearchCalled = false
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashUiState.Success,
                onNavigateToSearch = { navigateToSearchCalled = true },
                onNavigateToError = { }
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SPLASH_LOADING).assertDoesNotExist()
        assertTrue(navigateToSearchCalled)
    }

    @Test
    fun `test Splash screen - Error status`() {
        var errorType: ErrorType? = null
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashUiState.Error(ErrorType.UNKNOWN),
                onNavigateToSearch = { },
                onNavigateToError = { errorType = it }
            )
        }
        composeTestRule.onNodeWithTag(TestTags.SPLASH_LOADING).assertDoesNotExist()
        assertEquals(ErrorType.UNKNOWN, errorType)
    }
}
