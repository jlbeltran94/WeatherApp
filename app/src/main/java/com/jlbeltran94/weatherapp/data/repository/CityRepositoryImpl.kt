package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.mapper.toCity
import com.jlbeltran94.weatherapp.data.remote.CityApiService
import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val apiService: CityApiService,
    private val apiKey: String
) : CityRepository {

    override suspend fun searchCities(query: String): Result<List<City>> {
        return safeApiCall {
            val response = apiService.searchCities(apiKey, query)
            response.map { it.toCity() }
        }
    }
}
