package com.jlbeltran94.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jlbeltran94.searchlocationcomponent.data.local.dao.RecentSearchDao
import com.jlbeltran94.searchlocationcomponent.data.local.entity.RecentSearchEntity

@Database(
    entities = [RecentSearchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}