package com.jlbeltran94.weatherdetailcomponent.domain.repository

import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(cityQuery: String): Result<Weather>
}
