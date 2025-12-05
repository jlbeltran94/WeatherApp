package com.jlbeltran94.weatherapp.presentation.screens.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.navigation.Screen

fun NavGraphBuilder.addSearch(navController: NavHostController) {
    composable(Screen.Search.route) {
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
            }
        )
    }
}
