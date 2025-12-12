package com.jlbeltran94.searchlocationcomponent.di

import com.jlbeltran94.searchlocationcomponent.data.remote.CityApiService
import com.jlbeltran94.searchlocationcomponent.data.repository.CityRepositoryImpl
import com.jlbeltran94.searchlocationcomponent.data.local.dao.RecentSearchDao
import com.jlbeltran94.searchlocationcomponent.data.repository.RecentSearchesRepositoryImpl
import com.jlbeltran94.searchlocationcomponent.domain.repository.CityRepository
import com.jlbeltran94.searchlocationcomponent.domain.repository.RecentSearchesRepository
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
    fun provideCityRepository(
        apiService: CityApiService,
        apiKey: String
    ): CityRepository {
        return CityRepositoryImpl(apiService, apiKey)
    }

    @Provides
    @Singleton
    fun provideRecentSearchesRepository(recentSearchDao: RecentSearchDao): RecentSearchesRepository{
        return RecentSearchesRepositoryImpl(recentSearchDao)
    }
}