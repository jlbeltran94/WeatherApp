package com.jlbeltran94.weatherapp.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jlbeltran94.weatherapp.R
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.screens.search.components.CitiesResults
import com.jlbeltran94.weatherapp.presentation.screens.search.components.NoResultsFound
import com.jlbeltran94.weatherapp.presentation.screens.search.components.ResentSearch
import com.jlbeltran94.weatherapp.presentation.screens.search.components.SearchBar
import com.jlbeltran94.weatherapp.presentation.screens.search.components.SearchShimmer
import com.jlbeltran94.weatherapp.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToError: (ErrorType) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val dimens = AppTheme.dimens
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is SearchUiState.Error -> {
                keyboardController?.hide()
                onNavigateToError(state.errorType)
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_header),
                        style = typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimens.paddingLarge)
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onClearSearch = viewModel::clearSearch,
                onSearchQueryChange = viewModel::onSearchQueryChange
            )

            when (val state = uiState) {
                is SearchUiState.Idle -> {
                    ResentSearch(recentSearches, keyboardController, onNavigateToDetail)
                }

                is SearchUiState.Loading -> {
                    SearchShimmer()
                }

                is SearchUiState.Success -> {
                    CitiesResults(state.cities, keyboardController, onNavigateToDetail)
                }

                is SearchUiState.NoResults -> {
                    NoResultsFound()
                }

                is SearchUiState.Error -> {
                    // Handled inside LaunchedEffect
                }
            }
        }
    }
}
