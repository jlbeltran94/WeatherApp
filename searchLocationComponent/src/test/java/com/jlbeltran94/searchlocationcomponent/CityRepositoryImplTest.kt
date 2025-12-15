package com.jlbeltran94.searchlocationcomponent

import com.jlbeltran94.commonmodel.exception.DomainException
import com.jlbeltran94.searchlocationcomponent.data.remote.CityApiService
import com.jlbeltran94.searchlocationcomponent.data.remote.dto.SearchResponseDto
import com.jlbeltran94.searchlocationcomponent.data.repository.CityRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CityRepositoryImplTest {

    private lateinit var cityApiService: CityApiService
    private lateinit var cityRepository: CityRepositoryImpl

    @Before
    fun setUp() {
        cityApiService = mockk()
        cityRepository = CityRepositoryImpl(cityApiService)
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
        coEvery { cityApiService.searchCities(query) } returns searchResponse
        val result = cityRepository.searchCities(query)
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(1, result.getOrNull()?.size)
        Assert.assertEquals("London", result.getOrNull()?.first()?.name)
        coVerify(exactly = 1) { cityApiService.searchCities(query) }
    }

    @Test
    fun `searchCities should return failure result on api error`() = runBlocking {
        val query = "London"
        val exception = IOException("Network failed")
        coEvery { cityApiService.searchCities(query) } throws exception
        val result = cityRepository.searchCities(query)
        Assert.assertTrue(result.isFailure)
        Assert.assertTrue(result.exceptionOrNull() is DomainException.IOError)
    }
}