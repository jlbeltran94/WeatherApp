package com.jlbeltran94.weatherdetailcomponent.di

import com.jlbeltran94.weatherdetailcomponent.data.remote.WeatherApiService
import com.jlbeltran94.weatherdetailcomponent.data.repository.WeatherRepositoryImpl
import com.jlbeltran94.weatherdetailcomponent.domain.repository.WeatherRepository
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

}