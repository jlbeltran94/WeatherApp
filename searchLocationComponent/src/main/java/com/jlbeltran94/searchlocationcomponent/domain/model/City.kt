package com.jlbeltran94.searchlocationcomponent.domain.model

data class City(
    val id: String,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
