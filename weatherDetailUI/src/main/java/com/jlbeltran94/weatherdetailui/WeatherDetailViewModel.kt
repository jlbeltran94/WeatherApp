package com.jlbeltran94.weatherdetailui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlbeltran94.commonmodel.exception.DomainException
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch
import com.jlbeltran94.searchlocationcomponent.domain.usecase.SaveRecentSearchUseCase
import com.jlbeltran94.weatherdetailcomponent.data.mapper.toCity
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailcomponent.domain.usecase.GetWeatherUseCase
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
                saveRecentSearchUseCase(
                    RecentSearch(
                        id = "${weather.cityName},${weather.country}",
                        cityName = weather.cityName,
                        region = weather.region,
                        country = weather.country,
                        temperature = weather.temperature,
                        condition = weather.condition,
                        conditionIcon = weather.conditionIcon,
                        timestamp = System.currentTimeMillis()
                    )
                )
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

}

sealed class WeatherDetailUiState {
    object Loading : WeatherDetailUiState()
    data class Success(val weather: Weather) : WeatherDetailUiState()
    data class Error(val errorType: ErrorType) : WeatherDetailUiState()
}
