package com.jlbeltran94.searchlocationcomponent.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)