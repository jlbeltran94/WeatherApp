package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.searchlocationcomponent.di.RepositoryModule
import com.jlbeltran94.searchlocationcomponent.domain.repository.RecentSearchesRepository
import com.jlbeltran94.weatherdetailcomponent.domain.repository.WeatherRepository
import com.jlbeltran94.weatherdetailcomponent.di.RepositoryModule as WeatherRepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class, WeatherRepositoryModule::class]
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
