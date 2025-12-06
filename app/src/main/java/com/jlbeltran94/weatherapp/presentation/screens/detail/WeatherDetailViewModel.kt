package com.jlbeltran94.weatherapp.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlbeltran94.weatherapp.data.mapper.toCity
import com.jlbeltran94.weatherapp.domain.exception.DomainException
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.domain.usecase.GetWeatherUseCase
import com.jlbeltran94.weatherapp.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveRecentSearchUseCase: SaveRecentSearchUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cityQuery: String by lazy {
        savedStateHandle.get<String>("cityQuery")?.let {
            URLDecoder.decode(it, "UTF-8")
        }.orEmpty()
    }

    val uiState: StateFlow<WeatherDetailUiState> = flow {
        if (cityQuery.isBlank()) {
            WeatherDetailUiState.Error(ErrorType.UNKNOWN)
        }
        emit(getWeatherUseCase(cityQuery))
    }.map { result ->
        result.fold(
            onSuccess = { weather ->
                // Save to recent searches as a side-effect
                val city = weather.toCity()
                saveRecentSearchUseCase(city, weather)
                WeatherDetailUiState.Success(weather)
            },
            onFailure = { error ->
                val errorType = when (error) {
                    is DomainException.IOError -> ErrorType.IO_ERROR
                    else -> ErrorType.UNKNOWN
                }
                WeatherDetailUiState.Error(errorType)
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WeatherDetailUiState.Loading
    )

    /*fun loadWeather(cityQuery: String) {
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
    }*/
}

sealed class WeatherDetailUiState {
    object Loading : WeatherDetailUiState()
    data class Success(val weather: Weather) : WeatherDetailUiState()
    data class Error(val errorType: ErrorType) : WeatherDetailUiState()
}
