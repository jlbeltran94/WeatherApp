package com.jlbeltran94.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jlbeltran94.weatherapp.presentation.screens.detail.addDetail
import com.jlbeltran94.weatherapp.presentation.screens.error.addErrorScreens
import com.jlbeltran94.weatherapp.presentation.screens.search.addSearch
import com.jlbeltran94.weatherapp.presentation.screens.splash.addSplash

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
