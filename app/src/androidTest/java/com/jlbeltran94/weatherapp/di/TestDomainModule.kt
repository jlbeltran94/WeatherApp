package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.searchlocationcomponent.di.DomainModule
import com.jlbeltran94.searchlocationcomponent.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SearchCitiesUseCase
import com.jlbeltran94.weatherdetailcomponent.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton
import com.jlbeltran94.weatherdetailcomponent.di.DomainModule as WeatherDomainModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModule::class, WeatherDomainModule::class]
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
