package com.jlbeltran94.weatherdetailcomponent.domain.model

data class Weather(
    val cityId: String,
    val cityName: String,
    val cityLat: Double,
    val cityLon: Double,
    val region: String,
    val country: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val highTemp: Double?,
    val lowTemp: Double?,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val hourlyForecast: List<HourlyForecast>?,
    val dailyForecast: List<DailyForecast>?
)
