package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.domain.repository.CityRepository
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import com.jlbeltran94.weatherapp.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.weatherapp.domain.usecase.GetWeatherUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SaveRecentSearchUseCase
//import com.jlbeltran94.weatherapp.domain.usecase.SearchCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

//    @Provides
//    @ViewModelScoped
//    fun provideSearchCitiesUseCase(repository: CityRepository): SearchCitiesUseCase {
//        return SearchCitiesUseCase(repository)
//    }

    @Provides
    @ViewModelScoped
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetRecentSearchesUseCase(repository: RecentSearchesRepository): GetRecentSearchesUseCase {
        return GetRecentSearchesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveRecentSearchUseCase(repository: RecentSearchesRepository): SaveRecentSearchUseCase {
        return SaveRecentSearchUseCase(repository)
    }
}
