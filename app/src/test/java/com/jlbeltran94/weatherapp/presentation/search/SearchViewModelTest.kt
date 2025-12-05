package com.jlbeltran94.weatherapp.presentation.search

import app.cash.turbine.test
import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SearchCitiesUseCase
import com.jlbeltran94.weatherapp.presentation.screens.search.SearchUiState
import com.jlbeltran94.weatherapp.presentation.screens.search.SearchViewModel
import com.jlbeltran94.weatherapp.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var searchCitiesUseCase: SearchCitiesUseCase
    private lateinit var getRecentSearchesUseCase: GetRecentSearchesUseCase
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        searchCitiesUseCase = mockk()
        getRecentSearchesUseCase = mockk()
        // Mock initial recent searches
        every { getRecentSearchesUseCase() } returns flowOf(emptyList())
        viewModel = SearchViewModel(searchCitiesUseCase, getRecentSearchesUseCase)
    }

    @Test
    fun `initial state is Idle and recent searches are loaded`() = runTest(testDispatcher) {
        val recentSearches = listOf(RecentSearch("1", "London", "", "UK", 15.0, "Sunny", "", 1L))
        every { getRecentSearchesUseCase() } returns flowOf(recentSearches)
        val vm = SearchViewModel(searchCitiesUseCase, getRecentSearchesUseCase)
        advanceUntilIdle() // Allow collection of recent searches
        assertEquals(SearchUiState.Idle, vm.uiState.value)
        assertEquals(recentSearches, vm.recentSearches.value)
    }

    @Test
    fun `onSearchQueryChange updates query and triggers search`() = runTest(testDispatcher) {
        val query = "Paris"
        val cities = listOf(City("2", "Paris", "", "France", 0.0, 0.0))
        coEvery { searchCitiesUseCase(query) } returns Result.success(cities)

        viewModel.uiState.test {
            // Initial Idle state
            assertEquals(SearchUiState.Idle, awaitItem())

            // Trigger search
            viewModel.onSearchQueryChange(query)
            advanceUntilIdle() // Execute the debounced search

            // Loading state
            assertEquals(SearchUiState.Loading, awaitItem())

            // Success state
            val successState = awaitItem() as SearchUiState.Success
            assertEquals(cities, successState.cities)
        }
    }

    @Test
    fun `search returns NoResults when use case returns empty list`() = runTest(testDispatcher) {
        val query = "UnknownCity"
        coEvery { searchCitiesUseCase(query) } returns Result.success(emptyList())

        viewModel.uiState.test {
            assertEquals(SearchUiState.Idle, awaitItem())
            viewModel.onSearchQueryChange(query)
            advanceUntilIdle() // Execute the debounced search

            assertEquals(SearchUiState.Loading, awaitItem())
            assertTrue(awaitItem() is SearchUiState.NoResults)
        }
    }

    @Test
    fun `search returns Error when use case fails`() = runTest(testDispatcher) {
        val query = "ErrorCity"
        val exception = RuntimeException("Network Error")
        coEvery { searchCitiesUseCase(query) } returns Result.failure(exception)

        viewModel.uiState.test {
            assertEquals(SearchUiState.Idle, awaitItem())
            viewModel.onSearchQueryChange(query)
            advanceUntilIdle() // Execute the debounced search

            assertEquals(SearchUiState.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue(errorState is SearchUiState.Error)
        }
    }

    @Test
    fun `clearSearch resets query and returns to Idle`() = runTest(testDispatcher) {
        viewModel.onSearchQueryChange("some query")
        viewModel.clearSearch()

        assertEquals("", viewModel.searchQuery.value)
        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
    }
}
