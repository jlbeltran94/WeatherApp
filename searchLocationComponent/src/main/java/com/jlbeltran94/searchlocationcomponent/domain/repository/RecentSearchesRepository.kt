package com.jlbeltran94.searchlocationcomponent.domain.repository

import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchesRepository {
    fun getRecentSearches(): Flow<List<RecentSearch>>
    suspend fun saveRecentSearch(recentSearch: RecentSearch)
}
