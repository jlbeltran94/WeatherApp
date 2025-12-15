package com.jlbeltran94.weatherdetailcomponent.domain.usecase

import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailcomponent.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityQuery: String): Result<Weather> {
        return repository.getWeather(cityQuery)
    }
}
