package com.jlbeltran94.weatherdetailui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.jlbeltran94.commonmodel.exception.DomainException
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailcomponent.domain.usecase.GetWeatherUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.MainCoroutineRule
import java.io.IOException

@ExperimentalCoroutinesApi
class WeatherDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var saveRecentSearchUseCase: SaveRecentSearchUseCase
    private lateinit var viewModel: WeatherDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        getWeatherUseCase = mockk()
        saveRecentSearchUseCase = mockk(relaxed = true)
        savedStateHandle = mockk(relaxed = true)
        viewModel = WeatherDetailViewModel(
            getWeatherUseCase = getWeatherUseCase,
            saveRecentSearchUseCase = saveRecentSearchUseCase,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `loadWeather success`() = runTest(testDispatcher) {
        // Given
        val cityQuery = "London"
        val weather = mockk<Weather>(relaxed = true)
        every { savedStateHandle.get<String>("cityQuery") } returns cityQuery
        coEvery { getWeatherUseCase(cityQuery) } returns Result.success(weather)

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
        every { savedStateHandle.get<String>("cityQuery") } returns cityQuery
        coEvery { getWeatherUseCase(cityQuery) } returns Result.failure(exception)

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
        every { savedStateHandle.get<String>("cityQuery") } returns cityQuery
        coEvery { getWeatherUseCase(cityQuery) } returns Result.failure(exception)

        // Then
        viewModel.uiState.test {
            assertEquals(WeatherDetailUiState.Loading, awaitItem())
            val errorState = awaitItem() as WeatherDetailUiState.Error
            assertEquals(ErrorType.UNKNOWN, errorState.errorType)
        }
    }
}
