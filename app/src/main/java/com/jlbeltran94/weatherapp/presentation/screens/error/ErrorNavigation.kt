package com.jlbeltran94.weatherapp.presentation.screens.error

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.weatherapp.presentation.navigation.Screen

fun NavGraphBuilder.addErrorScreens(navController: NavHostController) {
    composable(Screen.NoNetwork.route) {
        NoNetworkScreen(
            onRetry = {
                navController.navigate(Screen.Search.route) {
                    popUpTo(0) { inclusive = true } // Clear the back stack and restart
                }
            }
        )
    }

    composable(Screen.UnexpectedError.route) {
        UnexpectedErrorScreen(
            onRetry = {
                navController.navigate(Screen.Search.route) {
                    popUpTo(0) { inclusive = true } // Clear the back stack and restart
                }
            }
        )
    }
}
