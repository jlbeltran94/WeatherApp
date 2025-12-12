package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.remote.WeatherApiService
import com.jlbeltran94.weatherapp.data.remote.dto.ConditionDto
import com.jlbeltran94.weatherapp.data.remote.dto.CurrentDto
import com.jlbeltran94.weatherapp.data.remote.dto.DayDto
import com.jlbeltran94.weatherapp.data.remote.dto.ForecastDayDto
import com.jlbeltran94.weatherapp.data.remote.dto.ForecastDto
import com.jlbeltran94.weatherapp.data.remote.dto.LocationDto
import com.jlbeltran94.weatherapp.data.remote.dto.SearchResponseDto
import com.jlbeltran94.weatherapp.data.remote.dto.WeatherResponseDto
import com.jlbeltran94.weatherapp.domain.exception.DomainException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class WeatherRepositoryImplTest {

    private lateinit var apiService: WeatherApiService
    private lateinit var weatherRepository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        apiService = mockk()
        weatherRepository = WeatherRepositoryImpl(apiService)
    }

    @Test
    fun `searchCities should return success result on api success`() = runBlocking {
        val query = "London"
        val searchResponse = listOf(
            SearchResponseDto(
                id = 1,
                name = "London",
                region = "City of London, Greater London",
                country = "United Kingdom",
                lat = 51.52,
                lon = -0.11,
                url = "london-city-of-london-greater-london-united-kingdom"
            )
        )
        coEvery { apiService.searchCities(query) } returns searchResponse
        val result = weatherRepository.searchCities(query)
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("London", result.getOrNull()?.first()?.name)
        coVerify(exactly = 1) { apiService.searchCities(query) }
    }

    @Test
    fun `searchCities should return failure result on api error`() = runBlocking {
        val query = "London"
        val exception = IOException("Network failed")
        coEvery { apiService.searchCities(query) } throws exception
        val result = weatherRepository.searchCities(query)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is DomainException.IOError)
    }

    @Test
    fun `getWeather should return success result on api success`() = runBlocking {
        val cityQuery = "London"
        val weatherResponse = mockWeatherResponseDto()
        coEvery { apiService.getForecastWeather(cityQuery, 7) } returns weatherResponse
        val result = weatherRepository.getWeather(cityQuery)
        assertTrue(result.isSuccess)
        assertEquals("London", result.getOrNull()?.cityName)
        assertEquals(15.0, result.getOrNull()?.temperature!!, 0.0)
        coVerify(exactly = 1) { apiService.getForecastWeather(cityQuery, 7) }
    }

    @Test
    fun `getWeather should return failure result on api error`() = runBlocking {
        val cityQuery = "London"
        val exception = IOException("Network failed")
        coEvery { apiService.getForecastWeather(cityQuery, 7) } throws exception

        val result = weatherRepository.getWeather(cityQuery)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is DomainException.IOError)
    }

    private fun mockWeatherResponseDto(): WeatherResponseDto {
        val location = LocationDto(
            name = "London",
            region = "Greater London",
            country = "United Kingdom",
            lat = 51.52,
            lon = -0.11
        )
        val condition = ConditionDto(
            text = "Sunny",
            icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
        )
        val current = CurrentDto(
            tempC = 15.0,
            condition = condition,
            feelsLikeC = 14.5,
            humidity = 70,
            windKph = 10.0
        )
        val day = DayDto(maxTempC = 20.0, minTempC = 10.0, condition = condition)
        val forecastDay = ForecastDayDto("2023-10-27", day, emptyList())
        val forecast = ForecastDto(listOf(forecastDay))
        return WeatherResponseDto(location, current, forecast)
    }
}
