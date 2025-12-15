package com.jlbeltran94.weatherdetailcomponent.data.remote

import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") query: String,
        @Query("days") days: Int = 7
    ): WeatherResponseDto
}
