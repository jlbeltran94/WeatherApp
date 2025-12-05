package com.jlbeltran94.weatherapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SearchCitiesUseCase
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<RecentSearch>>(emptyList<RecentSearch>())
    val recentSearches: StateFlow<List<RecentSearch>> = _recentSearches.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadRecentSearches()
        observeSearchQuery()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        _searchQuery
            .debounce(500)
            .onEach { query ->
                if (query.isBlank()) {
                    _uiState.value = SearchUiState.Idle
                } else {
                    searchCities(query)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchCities(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            searchCitiesUseCase(query).fold(
                onSuccess = { cities ->
                    if (cities.isEmpty()) {
                        _uiState.value = SearchUiState.NoResults
                    } else {
                        _uiState.value = SearchUiState.Success(cities)
                    }
                },
                onFailure = { error ->
                    _uiState.value = SearchUiState.Error(
                        if (error.message?.contains("network", ignoreCase = true) == true ||
                            error.message?.contains(
                                "Unable to resolve host",
                                ignoreCase = true
                            ) == true
                        ) {
                            ErrorType.IO_ERROR
                        } else {
                            ErrorType.UNKNOWN
                        }
                    )
                }
            )
        }
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            getRecentSearchesUseCase().collect { searches ->
                _recentSearches.value = searches
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _uiState.value = SearchUiState.Idle
    }
}

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val cities: List<City>) : SearchUiState()
    object NoResults : SearchUiState()
    data class Error(val errorType: ErrorType) : SearchUiState()
}
