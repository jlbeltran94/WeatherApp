package com.jlbeltran94.weatherapp.data.remote

import com.jlbeltran94.weatherapp.data.remote.dto.SearchResponseDto
import com.jlbeltran94.weatherapp.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("days") days: Int = 7
    ): WeatherResponseDto
}
