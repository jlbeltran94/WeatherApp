package com.jlbeltran94.searchlocationcomponent.domain.usecase

import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationcomponent.domain.repository.RecentSearchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchesUseCase @Inject constructor(
    private val repository: RecentSearchesRepository
) {
    operator fun invoke(): Flow<List<RecentSearch>> {
        return repository.getRecentSearches()
    }
}
