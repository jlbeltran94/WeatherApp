package com.jlbeltran94.weatherdetailcomponent.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
