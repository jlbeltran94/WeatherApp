package com.jlbeltran94.searchlocationcomponent.domain.usecase

import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.repository.CityRepository
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
