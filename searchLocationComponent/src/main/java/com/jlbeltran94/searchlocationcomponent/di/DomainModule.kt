package com.jlbeltran94.searchlocationcomponent.di

import com.jlbeltran94.searchlocationcomponent.domain.repository.CityRepository
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SearchCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideSearchCitiesUseCase(repository: CityRepository): SearchCitiesUseCase {
        return SearchCitiesUseCase(repository)
    }
}
