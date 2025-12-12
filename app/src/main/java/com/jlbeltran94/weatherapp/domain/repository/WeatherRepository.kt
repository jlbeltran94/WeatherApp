package com.jlbeltran94.weatherapp.domain.repository

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(cityQuery: String): Result<Weather>
}
