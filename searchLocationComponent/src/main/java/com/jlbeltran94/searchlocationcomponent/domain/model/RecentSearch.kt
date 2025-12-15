package com.jlbeltran94.searchlocationcomponent.domain.model

data class RecentSearch(
    val id: String,
    val cityName: String,
    val region: String,
    val country: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val timestamp: Long
)
