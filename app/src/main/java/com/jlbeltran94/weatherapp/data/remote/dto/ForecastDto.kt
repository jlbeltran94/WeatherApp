package com.jlbeltran94.weatherapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    val forecastday: List<ForecastDayDto>
)
