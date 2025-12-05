package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchCitiesUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var searchCitiesUseCase: SearchCitiesUseCase

    @Before
    fun setUp() {
        weatherRepository = mockk()
        searchCitiesUseCase = SearchCitiesUseCase(weatherRepository)
    }

    @Test
    fun `invoke should return success result on repository success`() = runBlocking {
        val query = "London"
        val cities = listOf(City("1", "London", "", "UK", 0.0, 0.0))
        val successResult = Result.success(cities)
        coEvery { weatherRepository.searchCities(query) } returns successResult

        val result = searchCitiesUseCase(query)

        coVerify(exactly = 1) { weatherRepository.searchCities(query) }
        assertEquals(successResult, result)
    }

    @Test
    fun `invoke should return failure result on repository failure`() = runBlocking {
        val query = "London"
        val exception = RuntimeException("Network error")
        val failureResult = Result.failure<List<City>>(exception)
        coEvery { weatherRepository.searchCities(query) } returns failureResult

        val result = searchCitiesUseCase(query)

        coVerify(exactly = 1) { weatherRepository.searchCities(query) }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
