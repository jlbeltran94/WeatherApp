package com.jlbeltran94.weatherapp.presentation.detail

import app.cash.turbine.test
import com.jlbeltran94.weatherapp.domain.exception.DomainException
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.usecase.GetWeatherUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.screens.detail.WeatherDetailUiState
import com.jlbeltran94.weatherapp.presentation.screens.detail.WeatherDetailViewModel
import com.jlbeltran94.weatherapp.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class WeatherDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var saveRecentSearchUseCase: SaveRecentSearchUseCase
    private lateinit var viewModel: WeatherDetailViewModel

    @Before
    fun setUp() {
        getWeatherUseCase = mockk()
        saveRecentSearchUseCase = mockk(relaxed = true)
        viewModel = WeatherDetailViewModel(getWeatherUseCase, saveRecentSearchUseCase)
    }

    @Test
    fun `loadWeather success`() = runTest(testDispatcher) {
        // Given
        val cityQuery = "London"
        val weather = mockk<Weather>(relaxed = true)
        coEvery { getWeatherUseCase(cityQuery) } returns Result.success(weather)

        // When
        viewModel.loadWeather(cityQuery)

        // Then
        viewModel.uiState.test {
            assertEquals(WeatherDetailUiState.Loading, awaitItem())
            assertEquals(WeatherDetailUiState.Success(weather), awaitItem())
        }
    }

    @Test
    fun `loadWeather network error`() = runTest(testDispatcher) {
        // Given
        val cityQuery = "London"
        val exception = DomainException.IOError(IOException())
        coEvery { getWeatherUseCase(cityQuery) } returns Result.failure(exception)

        // When
        viewModel.loadWeather(cityQuery)

        // Then
        viewModel.uiState.test {
            assertEquals(WeatherDetailUiState.Loading, awaitItem())
            val errorState = awaitItem() as WeatherDetailUiState.Error
            assertEquals(ErrorType.IO_ERROR, errorState.errorType)
        }
    }

    @Test
    fun `loadWeather unknown error`() = runTest(testDispatcher) {
        // Given
        val cityQuery = "London"
        val exception = DomainException.UnknownError(RuntimeException())
        coEvery { getWeatherUseCase(cityQuery) } returns Result.failure(exception)

        // When
        viewModel.loadWeather(cityQuery)

        // Then
        viewModel.uiState.test {
            assertEquals(WeatherDetailUiState.Loading, awaitItem())
            val errorState = awaitItem() as WeatherDetailUiState.Error
            assertEquals(ErrorType.UNKNOWN, errorState.errorType)
        }
    }
}
