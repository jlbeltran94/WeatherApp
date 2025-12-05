package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.local.dao.RecentSearchDao
import com.jlbeltran94.weatherapp.data.local.entity.RecentSearchEntity
import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.Weather
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecentSearchesRepositoryImplTest {

    private lateinit var recentSearchDao: RecentSearchDao
    private lateinit var recentSearchesRepository: RecentSearchesRepositoryImpl

    @Before
    fun setUp() {
        recentSearchDao = mockk()
        recentSearchesRepository = RecentSearchesRepositoryImpl(recentSearchDao)
    }

    @Test
    fun `getRecentSearches should return mapped flow from dao`() = runBlocking {
        val entities = listOf(
            RecentSearchEntity("1", "London", "", "UK", 15.0, "Sunny", "icon", System.currentTimeMillis())
        )
        every { recentSearchDao.getRecentSearches() } returns flowOf(entities)

        val result = recentSearchesRepository.getRecentSearches().first()

        assertEquals(1, result.size)
        assertEquals("London", result.first().cityName)
        coVerify(exactly = 1) { recentSearchDao.getRecentSearches() }
    }

    @Test
    fun `saveRecentSearch should call dao with correctly mapped entity`() = runBlocking {
        val city = City("1", "Paris", "Ile-de-France", "France", 48.85, 2.35)
        val weather = Weather(
            cityId = "1",
            cityName = "Paris",
            region = "Ile-de-France",
            country = "France",
            temperature = 20.0,
            condition = "Cloudy",
            conditionIcon = "icon_url",
            highTemp = 22.0,
            lowTemp = 18.0,
            feelsLike = 19.0,
            humidity = 80,
            windSpeed = 5.0,
            hourlyForecast = emptyList(),
            dailyForecast = emptyList(),
            cityLat = 48.85,
            cityLon = 2.35
        )
        val entitySlot = slot<RecentSearchEntity>()
        coEvery { recentSearchDao.insertRecentSearch(capture(entitySlot)) } returns Unit

        recentSearchesRepository.saveRecentSearch(city, weather)

        coVerify(exactly = 1) { recentSearchDao.insertRecentSearch(any()) }
        assertEquals("1", entitySlot.captured.id)
        assertEquals("Paris", entitySlot.captured.cityName)
        assertEquals(20.0, entitySlot.captured.temperature, 0.0)
        assertEquals("Cloudy", entitySlot.captured.condition)
    }
}
