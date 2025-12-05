package com.jlbeltran94.weatherapp.presentation.screens.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.navigation.Screen

fun NavGraphBuilder.addSplash(navController: NavHostController) {
    composable(Screen.Splash.route) {
        SplashScreen(
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
