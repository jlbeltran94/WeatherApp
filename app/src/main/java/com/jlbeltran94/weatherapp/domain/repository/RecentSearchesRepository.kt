package com.jlbeltran94.weatherapp.domain.repository

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface RecentSearchesRepository {
    fun getRecentSearches(): Flow<List<RecentSearch>>
    suspend fun saveRecentSearch(city: City, weather: Weather)
}
