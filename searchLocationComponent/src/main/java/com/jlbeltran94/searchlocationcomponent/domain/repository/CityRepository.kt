package com.jlbeltran94.searchlocationcomponent.domain.repository

import com.jlbeltran94.searchlocationcomponent.domain.model.City


interface CityRepository {
    suspend fun searchCities(query: String): Result<List<City>>
}