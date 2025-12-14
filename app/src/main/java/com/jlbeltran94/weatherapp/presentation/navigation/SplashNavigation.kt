package com.jlbeltran94.weatherapp.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.splashui.SplashScreen
import com.jlbeltran94.splashui.SplashViewModel

fun NavGraphBuilder.addSplash(navController: NavHostController) {
    composable(Screen.Splash.route) {
        val viewModel: SplashViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = true) {
            viewModel.performValidations()
        }
        SplashScreen(
            uiState = uiState,
            onNavigateToSearch = {
                navController.navigate(Screen.Search.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            },
            onNavigateToError = { errorType ->
                when (errorType) {
                    ErrorType.IO_ERROR -> navController.navigate(Screen.NoNetwork.route)
                    ErrorType.UNKNOWN -> navController.navigate(Screen.UnexpectedError.route)
                }
            }
        )
    }
}
