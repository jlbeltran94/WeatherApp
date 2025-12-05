package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import javax.inject.Inject

class SaveRecentSearchUseCase @Inject constructor(
    private val repository: RecentSearchesRepository
) {
    suspend operator fun invoke(city: City, weather: Weather) {
        repository.saveRecentSearch(city, weather)
    }
}
