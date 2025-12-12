package com.jlbeltran94.weatherapp.data.remote

import com.jlbeltran94.weatherapp.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {

    @GET("search.json")
    suspend fun searchCities(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): List<SearchResponseDto>
}
