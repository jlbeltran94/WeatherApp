package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.remote.CityApiService
import com.jlbeltran94.weatherapp.data.remote.dto.SearchResponseDto
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

class CityRepositoryImplTest {

    private lateinit var cityApiService: CityApiService
    private lateinit var cityRepository: CityRepositoryImpl
    private val apiKey = "test_api_key"

    @Before
    fun setUp() {
        cityApiService = mockk()
        cityRepository = CityRepositoryImpl(cityApiService, apiKey)
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
        coEvery { cityApiService.searchCities(apiKey, query) } returns searchResponse
        val result = cityRepository.searchCities(query)
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("London", result.getOrNull()?.first()?.name)
        coVerify(exactly = 1) { cityApiService.searchCities(apiKey, query) }
    }

    @Test
    fun `searchCities should return failure result on api error`() = runBlocking {
        val query = "London"
        val exception = IOException("Network failed")
        coEvery { cityApiService.searchCities(apiKey, query) } throws exception
        val result = cityRepository.searchCities(query)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is DomainException.IOError)
    }
}
