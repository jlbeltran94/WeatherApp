package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.data.local.dao.RecentSearchDao
import com.jlbeltran94.weatherapp.data.remote.WeatherApiService
import com.jlbeltran94.weatherapp.data.repository.RecentSearchesRepositoryImpl
import com.jlbeltran94.weatherapp.data.repository.WeatherRepositoryImpl
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: WeatherApiService,
        apiKey: String
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, apiKey)
    }

    @Provides
    @Singleton
    fun provideRecentSearchesRepository(recentSearchDao: RecentSearchDao): RecentSearchesRepository {
        return RecentSearchesRepositoryImpl(recentSearchDao)
    }
}
