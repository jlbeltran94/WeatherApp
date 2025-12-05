package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideRecentSearchesRepository(): RecentSearchesRepository {
        return mockk()
    }
}
