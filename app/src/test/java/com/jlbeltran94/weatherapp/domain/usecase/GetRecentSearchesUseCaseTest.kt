package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetRecentSearchesUseCaseTest {

    private lateinit var recentSearchesRepository: RecentSearchesRepository
    private lateinit var getRecentSearchesUseCase: GetRecentSearchesUseCase

    @Before
    fun setUp() {
        recentSearchesRepository = mockk()
        getRecentSearchesUseCase = GetRecentSearchesUseCase(recentSearchesRepository)
    }

    @Test
    fun `invoke should return flow of recent searches from repository`() = runBlocking {
        val recentSearches = listOf(
            RecentSearch(
                id = "1",
                cityName = "London",
                region = "",
                country = "UK",
                temperature = 15.0,
                condition = "Sunny",
                conditionIcon = "",
                timestamp = 12345L
            )
        )
        every { recentSearchesRepository.getRecentSearches() } returns flowOf(recentSearches)

        val result = getRecentSearchesUseCase.invoke().first()

        coVerify(exactly = 1) { recentSearchesRepository.getRecentSearches() }
        assertEquals(recentSearches, result)
    }
}
