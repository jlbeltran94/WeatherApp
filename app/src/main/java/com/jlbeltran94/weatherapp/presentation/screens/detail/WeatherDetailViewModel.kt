package com.jlbeltran94.weatherapp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlbeltran94.weatherapp.data.mapper.toCity
import com.jlbeltran94.weatherapp.domain.exception.DomainException
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.usecase.GetWeatherUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveRecentSearchUseCase: SaveRecentSearchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherDetailUiState>(WeatherDetailUiState.Loading)
    val uiState: StateFlow<WeatherDetailUiState> = _uiState

    fun loadWeather(cityQuery: String) {
        viewModelScope.launch {
            _uiState.value = WeatherDetailUiState.Loading
            getWeatherUseCase(cityQuery).fold(
                onSuccess = { weather ->
                    _uiState.value = WeatherDetailUiState.Success(weather)
                    // Save to recent searches - use city name as ID
                    val city = weather.toCity()
                    saveRecentSearchUseCase(city, weather)
                },
                onFailure = { error ->
                    val errorType = when (error) {
                        is DomainException.IOError -> ErrorType.IO_ERROR
                        else -> ErrorType.UNKNOWN
                    }
                    _uiState.value = WeatherDetailUiState.Error(errorType)
                }
            )
        }
    }
}

sealed class WeatherDetailUiState {
    object Loading : WeatherDetailUiState()
    data class Success(val weather: Weather) : WeatherDetailUiState()
    data class Error(val errorType: ErrorType) : WeatherDetailUiState()
}
