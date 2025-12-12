package com.jlbeltran94.weatherdetailcomponent.domain.model

data class HourlyForecast(
    val time: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String
)
