package com.jlbeltran94.weatherapp.presentation.screens.detail

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.navigation.Screen

fun NavGraphBuilder.addDetail(navController: NavHostController) {
    composable(Screen.Detail.route) { backStackEntry ->
        val viewModel: WeatherDetailViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        WeatherDetailScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToError = { errorType ->
                // Pop the detail screen first, then navigate to error
                navController.popBackStack()
                when (errorType) {
                    ErrorType.IO_ERROR -> navController.navigate(Screen.NoNetwork.route)
                    ErrorType.UNKNOWN -> navController.navigate(Screen.UnexpectedError.route)
                }
            },
            uiState = uiState
        )
    }
}
