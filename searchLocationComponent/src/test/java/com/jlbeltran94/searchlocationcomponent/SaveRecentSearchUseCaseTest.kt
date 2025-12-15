package com.jlbeltran94.searchlocationcomponent

import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationcomponent.domain.repository.RecentSearchesRepository
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SaveRecentSearchUseCase
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
        val recentSearch = RecentSearch(
            id = "1",
            cityName = "London",
            region = "",
            country = "UK",
            temperature = 0.0,
            condition = "",
            conditionIcon = "",
            timestamp = 0
        )
        coEvery { recentSearchesRepository.saveRecentSearch(any()) } returns Unit

        saveRecentSearchUseCase(recentSearch)

        coVerify(exactly = 1) { recentSearchesRepository.saveRecentSearch(recentSearch) }
    }
}
