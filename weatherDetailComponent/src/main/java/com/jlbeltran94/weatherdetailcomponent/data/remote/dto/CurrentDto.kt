package com.jlbeltran94.weatherdetailcomponent.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    @SerialName("temp_c")
    val tempC: Double,
    val condition: ConditionDto,
    @SerialName("feelslike_c")
    val feelsLikeC: Double,
    val humidity: Int,
    @SerialName("wind_kph")
    val windKph: Double
)
