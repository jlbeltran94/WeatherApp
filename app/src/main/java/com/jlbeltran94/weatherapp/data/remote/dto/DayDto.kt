package com.jlbeltran94.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayDto(
    @SerialName("maxtemp_c")
    val maxTempC: Double,
    @SerialName("mintemp_c")
    val minTempC: Double,
    val condition: ConditionDto
)
