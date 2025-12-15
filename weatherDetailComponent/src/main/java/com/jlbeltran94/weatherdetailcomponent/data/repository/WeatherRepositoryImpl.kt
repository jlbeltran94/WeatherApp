package com.jlbeltran94.weatherdetailcomponent.data.repository

import com.jlbeltran94.commonnetwork.safeApiCall
import com.jlbeltran94.weatherdetailcomponent.data.mapper.toWeather
import com.jlbeltran94.weatherdetailcomponent.data.remote.WeatherApiService
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailcomponent.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(cityQuery: String): Result<Weather> {
        return safeApiCall {
            val response = apiService.getForecastWeather(cityQuery, DAYS_IN_WEEK)
            response.toWeather()
        }
    }

    companion object {
        const val DAYS_IN_WEEK = 7
    }
}
