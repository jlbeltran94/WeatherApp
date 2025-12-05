package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.weatherapp.domain.usecase.GetWeatherUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SearchCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModule::class]
)
object TestDomainModule {

    @Provides
    @Singleton
    fun provideSearchCitiesUseCase(): SearchCitiesUseCase = mockk()

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(): GetWeatherUseCase = mockk()

    @Provides
    @Singleton
    fun provideGetRecentSearchesUseCase(): GetRecentSearchesUseCase = mockk()

    @Provides
    @Singleton
    fun provideSaveRecentSearchUseCase(): SaveRecentSearchUseCase = mockk(relaxed = true)
}
