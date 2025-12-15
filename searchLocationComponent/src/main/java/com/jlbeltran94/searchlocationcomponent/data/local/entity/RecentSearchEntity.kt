package com.jlbeltran94.searchlocationcomponent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey
    val id: String,
    val cityName: String,
    val region: String,
    val country: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val timestamp: Long
)
