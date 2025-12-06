package com.jlbeltran94.weatherapp.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.navigation.Screen

fun NavGraphBuilder.addSearch(navController: NavHostController) {
    composable(Screen.Search.route) {
        val viewModel: SearchViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
        val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()
        SearchScreen(
            onNavigateToDetail = {
                navController.navigate(Screen.Detail.createRoute(it)) {
                    launchSingleTop = true
                }
            },
            onNavigateToError = { errorType ->
                when (errorType) {
                    ErrorType.IO_ERROR -> navController.navigate(Screen.NoNetwork.route)
                    ErrorType.UNKNOWN -> navController.navigate(Screen.UnexpectedError.route)
                }
            },
            onClearSearch = viewModel::clearSearch,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            uiState = uiState,
            searchQuery = searchQuery,
            recentSearches = recentSearches,
            eventFlow = viewModel.eventFlow
        )
    }
}
