package com.jlbeltran94.weatherdetailui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.jlbeltran94.commonnetwork.di.withProtocol
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.commonui.TestTags
import com.jlbeltran94.commonui.theme.AppTheme
import com.jlbeltran94.commonui.theme.DarkBlue
import com.jlbeltran94.commonui.theme.SkyBlue
import com.jlbeltran94.commonui.theme.White
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailui.components.DailyForecastList
import com.jlbeltran94.weatherdetailui.components.DetailCard
import com.jlbeltran94.weatherdetailui.components.HourlyForecastList
import com.jlbeltran94.weatherdetailui.components.WeatherDetailShimmer

@Composable
fun WeatherDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToError: (ErrorType) -> Unit,
    uiState: WeatherDetailUiState
) {
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

@Composable
private fun HeaderDetailContent(weather: Weather) {
    Box(
        modifier = Modifier
            .height(AppTheme.dimens.detailHeaderHeight)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(
                    R.string.city_region_country,
                    weather.cityName,
                    weather.country
                ),
                style = typography.headlineSmall,
                color = White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacingLarge))
            Text(
                text = stringResource(
                    R.string.temperature_degrees,
                    weather.temperature.toInt()
                ),
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
                        weather.highTemp?.toInt() ?: 0,
                        weather.lowTemp?.toInt() ?: 0
                    ),
                    style = typography.bodyLarge,
                    color = White,
                    modifier = Modifier.testTag(TestTags.DETAIL_HIGH_LOW)
                )
            }
        }
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailContent(weather: Weather, onNavigateBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = scrollBehavior.state

    Scaffold(
        modifier = Modifier.testTag(TestTags.WEATHER_DETAIL_CONTENT),
        topBar = {
            Column(
                modifier = Modifier
                    .zIndex(0f)
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(colors = listOf(SkyBlue, DarkBlue)))
            ) {

                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(
                                R.string.city_region_country,
                                weather.cityName,
                                weather.country
                            ),
                            color = White.copy(alpha = scrollState.collapsedFraction),
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = DarkBlue
                    )
                )
                TopAppBar(
                    title = {
                        HeaderDetailContent(weather)
                    },
                    expandedHeight = AppTheme.dimens.detailHeaderHeight,
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = DarkBlue
                    ),
                    windowInsets = WindowInsets(0),
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorScheme.background)
                .padding(paddingValues)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState()),
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
    }
}
