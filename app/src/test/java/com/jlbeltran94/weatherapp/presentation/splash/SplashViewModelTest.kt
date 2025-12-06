package com.jlbeltran94.weatherapp.presentation.splash

import app.cash.turbine.test
import com.jlbeltran94.weatherapp.domain.util.NetworkMonitor
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.screens.splash.SplashUiState
import com.jlbeltran94.weatherapp.presentation.screens.splash.SplashViewModel
import com.jlbeltran94.weatherapp.util.MainCoroutineRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        networkMonitor = mockk()
    }

    @Test
    fun `performValidations navigates to search on success`() = runTest(testDispatcher) {
        every { networkMonitor.isNetworkAvailable() } returns true
        viewModel = SplashViewModel(networkMonitor, "VALID_API_KEY")

        viewModel.performValidations()
        advanceTimeBy(2100) // Advance past the 2-second delay

        viewModel.uiState.test {
            assertEquals(SplashUiState.Success, awaitItem())
        }
    }

    @Test
    fun `performValidations navigates to error on no network`() = runTest(testDispatcher) {
        every { networkMonitor.isNetworkAvailable() } returns false
        viewModel = SplashViewModel(networkMonitor, "VALID_API_KEY")

        viewModel.performValidations()
        advanceTimeBy(2100)

        viewModel.uiState.test {
            assertEquals(ErrorType.IO_ERROR, (awaitItem() as SplashUiState.Error).errorType)
        }
    }

    @Test
    fun `performValidations navigates to error on blank API key`() = runTest(testDispatcher) {
        every { networkMonitor.isNetworkAvailable() } returns true
        viewModel = SplashViewModel(networkMonitor, "YOUR_API_KEY_HERE") // Using placeholder value

        viewModel.performValidations()
        advanceTimeBy(2100)

        viewModel.uiState.test {
            assertEquals(ErrorType.UNKNOWN, (awaitItem() as SplashUiState.Error).errorType)
        }
    }
}
