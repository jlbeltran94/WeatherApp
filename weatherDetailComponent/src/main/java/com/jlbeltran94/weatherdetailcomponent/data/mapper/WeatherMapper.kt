package com.jlbeltran94.weatherdetailcomponent.data.mapper

import android.util.Log
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.weatherdetailcomponent.data.remote.dto.WeatherResponseDto
import com.jlbeltran94.weatherdetailcomponent.domain.model.DailyForecast
import com.jlbeltran94.weatherdetailcomponent.domain.model.HourlyForecast
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Weather.toCity(): City {
    return City(
        id = "$cityName,$country",
        name = cityName,
        region = region,
        country = country,
        lat = cityLat,
        lon = cityLon
    )
}

fun WeatherResponseDto.toWeather(): Weather {
    val hourlyForecast = forecast?.forecastday?.firstOrNull()?.hour
        ?.map {
            HourlyForecast(
                time = formatTime(it.time),
                temperature = it.tempC,
                condition = it.condition.text,
                conditionIcon = it.condition.icon
            )
        }

    val dailyForecast = forecast?.forecastday?.map {
        DailyForecast(
            day = formatDay(it.date),
            highTemp = it.day.maxTempC,
            lowTemp = it.day.minTempC,
            condition = it.day.condition.text,
            conditionIcon = it.day.condition.icon
        )
    }

    return Weather(
        cityId = location.name,
        cityName = location.name,
        region = location.region,
        country = location.country,
        temperature = current.tempC,
        condition = current.condition.text,
        conditionIcon = current.condition.icon,
        highTemp = forecast?.forecastday?.firstOrNull()?.day?.maxTempC,
        lowTemp = forecast?.forecastday?.firstOrNull()?.day?.minTempC,
        feelsLike = current.feelsLikeC,
        humidity = current.humidity,
        windSpeed = current.windKph,
        hourlyForecast = hourlyForecast,
        dailyForecast = dailyForecast,
        cityLat = location.lat,
        cityLon = location.lon
    )
}

internal fun formatTime(timeString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("h a", Locale.getDefault())
        val date = inputFormat.parse(timeString)
        date?.let { outputFormat.format(it) } ?: timeString
    } catch (e: ParseException) {
        Log.e("WeatherMapper", "Error parsing time: $timeString", e)
        timeString
    }
}

internal fun formatDay(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if (dateString == today) {
            "Today"
        } else {
            date?.let { outputFormat.format(it) } ?: dateString
        }
    } catch (e: ParseException) {
        Log.e("WeatherMapper", "Error parsing day: $dateString", e)
        dateString
    }
}
