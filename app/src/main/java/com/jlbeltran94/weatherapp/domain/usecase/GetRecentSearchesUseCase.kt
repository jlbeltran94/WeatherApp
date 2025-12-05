package com.jlbeltran94.weatherapp.domain.usecase

import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.domain.repository.RecentSearchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchesUseCase @Inject constructor(
    private val repository: RecentSearchesRepository
) {
    operator fun invoke(): Flow<List<RecentSearch>> {
        return repository.getRecentSearches()
    }
}
