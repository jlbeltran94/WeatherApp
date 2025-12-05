package com.jlbeltran94.weatherapp.presentation.screens.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.navigation.Screen
import java.net.URLDecoder

fun NavGraphBuilder.addDetail(navController: NavHostController) {
    composable(Screen.Detail.route) { backStackEntry ->
        val cityQuery = backStackEntry.arguments?.getString("cityQuery")?.let {
            URLDecoder.decode(it, "UTF-8")
        } ?: ""
        WeatherDetailScreen(
            cityQuery = cityQuery,
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
            }
        )
    }
}
