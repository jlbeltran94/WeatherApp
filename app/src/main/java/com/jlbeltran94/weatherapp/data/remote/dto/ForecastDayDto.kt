package com.jlbeltran94.weatherapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDto(
    val date: String,
    val day: DayDto,
    val hour: List<HourDto>? = null
)
