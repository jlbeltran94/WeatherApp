package com.jlbeltran94.weatherdetailcomponent.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto? = null
)
