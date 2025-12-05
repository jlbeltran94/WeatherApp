package com.jlbeltran94.weatherapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto? = null
)
