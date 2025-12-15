package com.jlbeltran94.weatherdetailcomponent

import com.jlbeltran94.commonmodel.exception.DomainException
import com.jlbeltran94.weatherdetailcomponent.data.remote.WeatherApiService
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.ConditionDto
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.CurrentDto
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.DayDto
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.ForecastDayDto
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.ForecastDto
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.LocationDto
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.WeatherResponseDto
import com.jlbeltran94.weatherdetailcomponent.data.repository.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class WeatherRepositoryImplTest {

    private lateinit var weatherApiService: WeatherApiService
    private lateinit var weatherRepository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        weatherApiService = mockk()
        weatherRepository = WeatherRepositoryImpl(weatherApiService)
    }

    @Test
    fun `getWeather should return success result on api success`() = runBlocking {
        val cityQuery = "London"
        val weatherResponse = mockWeatherResponseDto()
        coEvery {
            weatherApiService.getForecastWeather(
                cityQuery,
                7
            )
        } returns weatherResponse
        val result = weatherRepository.getWeather(cityQuery)
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals("London", result.getOrNull()?.cityName)
        Assert.assertEquals(15.0, result.getOrNull()?.temperature!!, 0.0)
        coVerify(exactly = 1) { weatherApiService.getForecastWeather(cityQuery, 7) }
    }

    @Test
    fun `getWeather should return failure result on api error`() = runBlocking {
        val cityQuery = "London"
        val exception = IOException("Network failed")
        coEvery { weatherApiService.getForecastWeather(cityQuery, 7) } throws exception

        val result = weatherRepository.getWeather(cityQuery)

        Assert.assertTrue(result.isFailure)
        Assert.assertTrue(result.exceptionOrNull() is DomainException.IOError)
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