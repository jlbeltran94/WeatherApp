package com.jlbeltran94.weatherapp.domain.repository

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun searchCities(query: String): Result<List<City>>
    suspend fun getWeather(cityQuery: String): Result<Weather>
}
