package com.jlbeltran94.weatherapp.presentation.util.extensions

import android.os.Build

/**
 * Prepends "https" or "http" to a URL string based on the Android API level.
 * WeatherAPI returns icon URLs starting with //cdn.weatherapi.com/...,
 * so we need to prepend the protocol to make it a valid URL.
 *
 * For Android P (API 28) and above, HTTPS is used. For older versions, HTTP is used
 * as a fallback, because of issues with cleartext traffic restrictions.
 */
fun String.withProtocol(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        "https:$this"
    } else {
        "http:$this"
    }
}
