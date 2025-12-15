package com.jlbeltran94.searchlocationcomponent.data.remote

import com.jlbeltran94.searchlocationcomponent.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {

    @GET("search.json")
    suspend fun searchCities(
        @Query("q") query: String
    ): List<SearchResponseDto>
}
