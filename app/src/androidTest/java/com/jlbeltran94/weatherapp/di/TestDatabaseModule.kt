package com.jlbeltran94.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.jlbeltran94.database.DatabaseModule
import com.jlbeltran94.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideInMemoryDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            .allowMainThreadQueries() // For simplicity in tests
            .build()
    }
}