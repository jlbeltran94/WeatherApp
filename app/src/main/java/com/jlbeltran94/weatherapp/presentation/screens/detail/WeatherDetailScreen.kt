package com.jlbeltran94.weatherapp.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jlbeltran94.weatherapp.R
import com.jlbeltran94.weatherapp.domain.model.Weather
import com.jlbeltran94.weatherapp.presentation.components.CollapsingToolbar
import com.jlbeltran94.weatherapp.presentation.navigation.ErrorType
import com.jlbeltran94.weatherapp.presentation.screens.detail.components.DailyForecastList
import com.jlbeltran94.weatherapp.presentation.screens.detail.components.DetailCard
import com.jlbeltran94.weatherapp.presentation.screens.detail.components.HourlyForecastList
import com.jlbeltran94.weatherapp.presentation.screens.detail.components.WeatherDetailShimmer
import com.jlbeltran94.weatherapp.presentation.theme.AppTheme
import com.jlbeltran94.weatherapp.presentation.theme.DarkBlue
import com.jlbeltran94.weatherapp.presentation.theme.SkyBlue
import com.jlbeltran94.weatherapp.presentation.theme.White
import com.jlbeltran94.weatherapp.presentation.util.TestTags
import com.jlbeltran94.weatherapp.presentation.util.extensions.withProtocol

@Composable
fun WeatherDetailScreen(
    cityQuery: String,
    onNavigateBack: () -> Unit,
    onNavigateToError: (ErrorType) -> Unit,
    viewModel: WeatherDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(cityQuery) {
        viewModel.loadWeather(cityQuery)
    }

    when (val state = uiState) {
        is WeatherDetailUiState.Loading -> {
            WeatherDetailShimmer()
        }
        is WeatherDetailUiState.Success -> {
            WeatherDetailContent(weather = state.weather, onNavigateBack = onNavigateBack)
        }
        is WeatherDetailUiState.Error -> {
            onNavigateToError(state.errorType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailContent(weather: Weather, onNavigateBack: () -> Unit) {
    CollapsingToolbar(
        header = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = listOf(SkyBlue, DarkBlue)))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = AppTheme.dimens.toolbarHeight),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = stringResource(R.string.city_region_country, weather.cityName, weather.country),
                        style = typography.headlineSmall,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(AppTheme.dimens.spacingLarge))
                    Text(
                        text = stringResource(R.string.temperature_degrees, weather.temperature.toInt()),
                        style = typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.testTag(TestTags.DETAIL_TEMPERATURE)
                    )
                    Spacer(modifier = Modifier.height(AppTheme.dimens.spacingMedium))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = weather.condition,
                            style = typography.titleMedium,
                            color = White,
                            modifier = Modifier.testTag(TestTags.DETAIL_CONDITION)
                        )
                        AsyncImage(
                            model = weather.conditionIcon.withProtocol(),
                            contentDescription = weather.condition,
                            modifier = Modifier.size(AppTheme.dimens.iconSizeMediumLarge)
                        )
                    }
                    if (weather.highTemp != null && weather.lowTemp != null) {
                        Spacer(modifier = Modifier.height(AppTheme.dimens.spacingMedium))
                        Text(
                            text = stringResource(
                                R.string.high_low_temperature,
                                weather.highTemp.toInt(),
                                weather.lowTemp.toInt()
                            ),
                            style = typography.bodyLarge,
                            color = White,
                            modifier = Modifier.testTag(TestTags.DETAIL_HIGH_LOW)
                        )
                    }
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .background(colorScheme.background)
                    .padding(AppTheme.dimens.paddingLarge),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacingLarge)
            ) {
                HourlyForecastList(weather)
                DailyForecastList(weather)
                ExtraDetails(weather)
                DetailCard(
                    title = stringResource(R.string.wind_speed),
                    value = stringResource(R.string.wind_speed_value, weather.windSpeed.toInt()),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        toolbar = { collapseFraction ->
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.city_region_country, weather.cityName, weather.country),
                        color = White.copy(alpha = collapseFraction),
                        style = typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description),
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue.copy(alpha = collapseFraction))
            )
        },
        headerHeight = AppTheme.dimens.detailHeaderHeight,
        toolbarHeight = AppTheme.dimens.toolbarHeight
    )
}

@Composable
fun ExtraDetails(
    weather: Weather
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacingMediumLarge)
    ) {
        DetailCard(
            title = stringResource(R.string.feels_like),
            value = stringResource(
                R.string.temperature_degrees,
                weather.feelsLike.toInt()
            ),
            modifier = Modifier.weight(1f)
        )
        DetailCard(
            title = stringResource(R.string.humidity),
            value = stringResource(R.string.humidity_value, weather.humidity),
            modifier = Modifier.weight(1f)
        )
    }
}
