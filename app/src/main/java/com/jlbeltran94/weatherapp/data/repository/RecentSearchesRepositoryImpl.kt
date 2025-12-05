package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.local.dao.RecentSearchDao
import com.jlbeltran94.weatherapp.data.local.entity.RecentSearchEntity
import com.jlbeltran94.weatherapp.data.mapper.toRecentSearch
import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchesRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
) : RecentSearchesRepository {

    override fun getRecentSearches(): Flow<List<RecentSearch>> {
        return recentSearchDao.getRecentSearches().map { entities ->
            entities.map { it.toRecentSearch() }
        }
    }

    override suspend fun saveRecentSearch(city: City, weather: Weather) {
        val entity = RecentSearchEntity(
            id = city.id,
            cityName = city.name,
            region = city.region,
            country = city.country,
            temperature = weather.temperature,
            condition = weather.condition,
            conditionIcon = weather.conditionIcon,
            timestamp = System.currentTimeMillis()
        )
        recentSearchDao.insertRecentSearch(entity)
    }
}
