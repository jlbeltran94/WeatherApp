package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.repository.CityRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(query: String): Result<List<City>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return repository.searchCities(query)
    }
}
