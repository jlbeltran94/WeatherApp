package com.jlbeltran94.searchlocationcomponent

import com.jlbeltran94.searchlocationcomponent.data.local.dao.RecentSearchDao
import com.jlbeltran94.searchlocationcomponent.data.local.entity.RecentSearchEntity
import com.jlbeltran94.searchlocationcomponent.data.repository.RecentSearchesRepositoryImpl
import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
            RecentSearchEntity(
                "1",
                "London",
                "",
                "UK",
                15.0,
                "Sunny",
                "icon",
                System.currentTimeMillis()
            )
        )
        every { recentSearchDao.getRecentSearches() } returns flowOf(entities)

        val result = recentSearchesRepository.getRecentSearches().first()

        Assert.assertEquals(1, result.size)
        Assert.assertEquals("London", result.first().cityName)
        coVerify(exactly = 1) { recentSearchDao.getRecentSearches() }
    }

    @Test
    fun `saveRecentSearch should call dao with correctly mapped entity`() = runBlocking {
        val recentSearch = RecentSearch(
            id = "1",
            cityName = "Paris",
            region = "Paris",
            country = "France",
            temperature = 20.0,
            condition = "Cloudy",
            conditionIcon = "cloudyIcon",
            timestamp = 7921
        )
        val entitySlot = slot<RecentSearchEntity>()
        coEvery { recentSearchDao.insertRecentSearch(capture(entitySlot)) } returns Unit

        recentSearchesRepository.saveRecentSearch(recentSearch)

        coVerify(exactly = 1) { recentSearchDao.insertRecentSearch(any()) }
        Assert.assertEquals("1", entitySlot.captured.id)
        Assert.assertEquals("Paris", entitySlot.captured.cityName)
        Assert.assertEquals(20.0, entitySlot.captured.temperature, 0.0)
        Assert.assertEquals("Cloudy", entitySlot.captured.condition)
    }
}
