package com.jlbeltran94.weatherapp.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.jlbeltran94.weatherapp.data.local.WeatherDatabase
import com.jlbeltran94.weatherapp.data.local.entity.RecentSearchEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class RecentSearchDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: WeatherDatabase
    private lateinit var recentSearchDao: RecentSearchDao

    @Before
    fun setup() {
        hiltRule.inject()
        recentSearchDao = database.recentSearchDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetRecentSearches() = runBlocking {
        
        val search1 = RecentSearchEntity("1", "London", "", "UK", 10.0, "Cloudy", "", 1000L)
        val search2 = RecentSearchEntity("2", "Paris", "", "France", 15.0, "Sunny", "", 2000L)

        
        recentSearchDao.insertRecentSearch(search1)
        recentSearchDao.insertRecentSearch(search2)

        
        val searches = recentSearchDao.getRecentSearches().first()
        assertEquals(2, searches.size)
        assertEquals(search2, searches[0]) // Should be most recent
        assertEquals(search1, searches[1])
    }

    @Test
    fun insertDuplicateReplacesExisting() = runBlocking {
        
        val search1 = RecentSearchEntity("1", "London", "", "UK", 10.0, "Cloudy", "", 1000L)
        val search2 = RecentSearchEntity("1", "London", "", "UK", 15.0, "Sunny", "", 2000L)

        
        recentSearchDao.insertRecentSearch(search1)
        recentSearchDao.insertRecentSearch(search2)

        
        val searches = recentSearchDao.getRecentSearches().first()
        assertEquals(1, searches.size)
        assertEquals(search2, searches.first()) // The newer one should have replaced the old one
    }

    @Test
    fun getRecentSearchesReturnsEmptyListWhenNoSearches() = runBlocking {
        val searches = recentSearchDao.getRecentSearches().first()
        assertEquals(0, searches.size)
    }
}
