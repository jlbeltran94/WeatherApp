package com.jlbeltran94.weatherapp.domain.repository

import com.jlbeltran94.weatherapp.domain.model.City

interface CityRepository {
    suspend fun searchCities(query: String): Result<List<City>>
}