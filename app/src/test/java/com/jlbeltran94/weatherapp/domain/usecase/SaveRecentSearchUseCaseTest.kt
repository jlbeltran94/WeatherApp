package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveRecentSearchUseCaseTest {

    private lateinit var recentSearchesRepository: RecentSearchesRepository
    private lateinit var saveRecentSearchUseCase: SaveRecentSearchUseCase

    @Before
    fun setUp() {
        recentSearchesRepository = mockk()
        saveRecentSearchUseCase = SaveRecentSearchUseCase(recentSearchesRepository)
    }

    @Test
    fun `invoke should call saveRecentSearch on repository`() = runBlocking {
        val city = City("1", "London", "", "UK", 0.0, 0.0)
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
        coEvery { recentSearchesRepository.saveRecentSearch(any(), any()) } returns Unit

        saveRecentSearchUseCase(city, weather)

        coVerify(exactly = 1) { recentSearchesRepository.saveRecentSearch(city, weather) }
    }
}
