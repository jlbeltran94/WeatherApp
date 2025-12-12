package com.jlbeltran94.weatherapp.data.repository

import com.jlbeltran94.weatherapp.data.mapper.toCity
import com.jlbeltran94.weatherapp.data.mapper.toWeather
import com.jlbeltran94.weatherapp.data.remote.WeatherApiService
import com.jlbeltran94.weatherapp.domain.exception.DomainException
import com.jlbeltran94.weatherapp.domain.model.City
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.repository.WeatherRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun searchCities(query: String): Result<List<City>> {
        return safeApiCall {
            val response = apiService.searchCities(query)
            response.map { it.toCity() }
        }
    }

    override suspend fun getWeather(cityQuery: String): Result<Weather> {
        return safeApiCall {
            val response = apiService.getForecastWeather(cityQuery, DAYS_IN_WEEK)
            response.toWeather()
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            Result.failure(DomainException.ApiError(e.code(), errorMessage, e))
        } catch (e: IOException) {
            Result.failure(DomainException.IOError(e))
        } catch (e: Exception) {
            Result.failure(DomainException.UnknownError(e))
        }
    }

    companion object {
        const val DAYS_IN_WEEK = 7
    }
}
