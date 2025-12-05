package com.jlbeltran94.weatherapp.presentation.navigation

import java.net.URLEncoder

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Search : Screen("search")
    object Detail : Screen("detail/{cityQuery}") {
        fun createRoute(cityQuery: String): String {
            val encodedCityQuery = URLEncoder.encode(cityQuery, "UTF-8")
            return "detail/$encodedCityQuery"
        }
    }
    object NoNetwork : Screen("no_network")
    object UnexpectedError : Screen("unexpected_error")
}
