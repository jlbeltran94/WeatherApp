package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.mapper.toWeather
import com.jlbeltran94.weatherapp.data.remote.WeatherApiService
import com.jlbeltran94.weatherapp.domain.exception.DomainException
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val apiKey: String
) : WeatherRepository {
    override suspend fun getWeather(cityQuery: String): Result<Weather> {
        return safeApiCall {
            val response = apiService.getForecastWeather(apiKey, cityQuery, DAYS_IN_WEEK)
            response.toWeather()
        }
    }

    companion object {
        const val DAYS_IN_WEEK = 7
    }
}

