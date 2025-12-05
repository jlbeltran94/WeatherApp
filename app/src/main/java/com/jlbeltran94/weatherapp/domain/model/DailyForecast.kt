package com.jlbeltran94.weatherapp.domain.model

data class DailyForecast(
    val day: String,
    val highTemp: Double,
    val lowTemp: Double,
    val condition: String,
    val conditionIcon: String
)
