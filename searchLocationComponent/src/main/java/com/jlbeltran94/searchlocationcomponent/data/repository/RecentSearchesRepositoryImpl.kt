package com.jlbeltran94.searchlocationcomponent.data.repository

import com.jlbeltran94.searchlocationcomponent.data.local.dao.RecentSearchDao
import com.jlbeltran94.searchlocationcomponent.data.mapper.toRecentSearch
import com.jlbeltran94.searchlocationcomponent.data.mapper.toRecentSearchEntity
import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationcomponent.domain.repository.RecentSearchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchesRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
) : RecentSearchesRepository {

    override fun getRecentSearches(): Flow<List<RecentSearch>> {
        return recentSearchDao.getRecentSearches().map { entities ->
            entities.map { it.toRecentSearch() }
        }
    }

    override suspend fun saveRecentSearch(recentSearch: RecentSearch) {
        val entity = recentSearch.toRecentSearchEntity()
        recentSearchDao.insertRecentSearch(entity)
    }
}