package com.jlbeltran94.searchlocationcomponent.domain.usecase

import javax.inject.Inject

class SaveRecentSearchUseCase @Inject constructor(
    private val repository: RecentSearchesRepository
) {
    suspend operator fun invoke(city: City, weather: Weather) {
        repository.saveRecentSearch(city, weather)
    }
}