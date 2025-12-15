package com.jlbeltran94.searchlocationui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlbeltran94.commonmodel.exception.DomainException
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationcomponent.domain.usecase.GetRecentSearchesUseCase
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                    when (error) {
                        is DomainException.ApiError -> {
                            viewModelScope.launch {
                                error.message?.let {
                                    _eventFlow.emit(UiEvent.ShowSnackbar(it))
                                }
                            }
                            _uiState.value = SearchUiState.NoResults
                        }

                        is DomainException.IOError -> {
                            _uiState.value = SearchUiState.Error(ErrorType.IO_ERROR)
                        }

                        else -> _uiState.value = SearchUiState.Error(ErrorType.UNKNOWN)
                    }
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

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
}
