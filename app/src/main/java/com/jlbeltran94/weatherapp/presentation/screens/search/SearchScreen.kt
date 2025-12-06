package com.jlbeltran94.weatherapp.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.jlbeltran94.weatherapp.R
import com.jlbeltran94.weatherapp.domain.model.RecentSearch
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.screens.search.components.CitiesResults
import com.jlbeltran94.weatherapp.presentation.screens.search.components.NoResultsFound
import com.jlbeltran94.weatherapp.presentation.screens.search.components.ResentSearch
import com.jlbeltran94.weatherapp.presentation.screens.search.components.SearchBar
import com.jlbeltran94.weatherapp.presentation.screens.search.components.SearchShimmer
import com.jlbeltran94.weatherapp.presentation.theme.AppTheme.dimens
import com.jlbeltran94.weatherapp.presentation.util.TestTags
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToError: (ErrorType) -> Unit,
    onClearSearch: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    uiState: SearchUiState,
    searchQuery: String,
    recentSearches: List<RecentSearch>,
    eventFlow: SharedFlow<UiEvent>
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    keyboardController?.hide()
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(modifier = Modifier.testTag(TestTags.ERROR_SNACKBAR), hostState = snackbarHostState) },
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
                onClearSearch = onClearSearch,
                onSearchQueryChange = onSearchQueryChange
            )

            when (uiState) {
                is SearchUiState.Idle -> {
                    ResentSearch(recentSearches, keyboardController, onNavigateToDetail)
                }

                is SearchUiState.Loading -> {
                    SearchShimmer()
                }

                is SearchUiState.Success -> {
                    CitiesResults(uiState.cities, keyboardController, onNavigateToDetail)
                }

                is SearchUiState.NoResults -> {
                    NoResultsFound()
                }

                is SearchUiState.Error -> {
                    keyboardController?.hide()
                    onNavigateToError(uiState.errorType)
                }
            }
        }
    }
}
