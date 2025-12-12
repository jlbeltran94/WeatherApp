package com.jlbeltran94.weatherdetailcomponent.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    val forecastday: List<ForecastDayDto>
)
