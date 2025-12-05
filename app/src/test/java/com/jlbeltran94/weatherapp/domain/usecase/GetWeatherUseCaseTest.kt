package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetWeatherUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setUp() {
        weatherRepository = mockk()
        getWeatherUseCase = GetWeatherUseCase(weatherRepository)
    }

    @Test
    fun `invoke should return success result on repository success`() = runBlocking {
        val cityQuery = "London"
        val weather = Weather(
            cityId = "1",
            cityName = "London",
            region = "",
            country = "UK",
            temperature = 15.0,
            condition = "Sunny",
            conditionIcon = "",
            highTemp = null,
            lowTemp = null,
            feelsLike = 14.0,
            humidity = 50,
            windSpeed = 10.0,
            hourlyForecast = null,
            dailyForecast = null,
            cityLat = 0.0,
            cityLon = 0.0
        )
        val successResult = Result.success(weather)
        coEvery { weatherRepository.getWeather(cityQuery) } returns successResult
        val result = getWeatherUseCase(cityQuery)
        coVerify(exactly = 1) { weatherRepository.getWeather(cityQuery) }
        assertEquals(successResult, result)
    }

    @Test
    fun `invoke should return failure result on repository failure`() = runBlocking {
        val cityQuery = "London"
        val exception = RuntimeException("Network error")
        val failureResult = Result.failure<Weather>(exception)
        coEvery { weatherRepository.getWeather(cityQuery) } returns failureResult
        val result = getWeatherUseCase(cityQuery)
        coVerify(exactly = 1) { weatherRepository.getWeather(cityQuery) }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
