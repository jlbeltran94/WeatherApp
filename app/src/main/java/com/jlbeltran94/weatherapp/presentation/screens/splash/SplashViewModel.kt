package com.jlbeltran94.weatherapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.commonnetwork.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val apiKey: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    private var validationJob: Job? = null

    fun performValidations() {
        // Cancel any existing validation job
        validationJob?.cancel()

        validationJob = viewModelScope.launch {
            _uiState.value = SplashUiState.Loading
            delay(2000) // Show splash for at least 2 seconds

            // Check network connectivity
            if (!networkMonitor.isNetworkAvailable()) {
                _uiState.value = SplashUiState.Error(ErrorType.IO_ERROR)
                return@launch
            }

            // Check API key (basic validation - just check if it's not the placeholder)
            if (apiKey == "YOUR_API_KEY_HERE" || apiKey.isBlank()) {
                _uiState.value = SplashUiState.Error(ErrorType.UNKNOWN)
                return@launch
            }
            _uiState.value = SplashUiState.Success
        }
    }
}

sealed class SplashUiState {
    object Loading : SplashUiState()
    object Success : SplashUiState()
    data class Error(val errorType: ErrorType) : SplashUiState()
}
