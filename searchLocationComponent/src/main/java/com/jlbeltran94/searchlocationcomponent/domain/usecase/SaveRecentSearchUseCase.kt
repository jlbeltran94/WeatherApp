package com.jlbeltran94.searchlocationcomponent.domain.usecase

import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationcomponent.domain.repository.RecentSearchesRepository
import javax.inject.Inject

class SaveRecentSearchUseCase @Inject constructor(
    private val repository: RecentSearchesRepository
) {
    suspend operator fun invoke(recentSearch: RecentSearch) {
        repository.saveRecentSearch(recentSearch)
    }
}
