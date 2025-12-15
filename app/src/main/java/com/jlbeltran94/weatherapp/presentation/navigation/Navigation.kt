package com.jlbeltran94.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun WeatherNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        addSplash(navController)
        addSearch(navController)
        addDetail(navController)
        addErrorScreens(navController)
    }
}
