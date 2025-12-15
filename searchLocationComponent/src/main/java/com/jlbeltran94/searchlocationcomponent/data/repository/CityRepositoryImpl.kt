package com.jlbeltran94.searchlocationcomponent.data.repository

import com.jlbeltran94.commonnetwork.safeApiCall
import com.jlbeltran94.searchlocationcomponent.data.mapper.toCity
import com.jlbeltran94.searchlocationcomponent.data.remote.CityApiService
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val apiService: CityApiService
) : CityRepository {

    override suspend fun searchCities(query: String): Result<List<City>> {
        return safeApiCall {
            val response = apiService.searchCities(query)
            response.map { it.toCity() }
        }
    }
}
