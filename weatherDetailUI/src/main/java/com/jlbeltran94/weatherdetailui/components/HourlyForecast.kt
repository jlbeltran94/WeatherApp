package com.jlbeltran94.weatherdetailui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.jlbeltran94.commonnetwork.di.withProtocol
import com.jlbeltran94.commonui.theme.AppTheme.dimens
import com.jlbeltran94.weatherdetailcomponent.domain.model.HourlyForecast
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailui.R

@Composable
fun HourlyForecastList(
    weather: Weather,
) {
    if (!weather.hourlyForecast.isNullOrEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimens.spacingLarge),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(dimens.paddingLarge)) {
                Text(
                    text = stringResource(R.string.hourly_forecast),
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = dimens.paddingMediumLarge)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(dimens.spacingMediumLarge)) {
                    weather.hourlyForecast?.let { hourlyForecast ->
                        items(hourlyForecast) { hourly -> HourlyForecastItem(hourly) }
                    }
                }
            }
        }
    }
}

@Composable
fun HourlyForecastItem(hourly: HourlyForecast) {
    Column(
        modifier = Modifier.width(dimens.hourlyItemWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hourly.time,
            style = typography.bodySmall,
            color = colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(dimens.spacingMedium))
        AsyncImage(
            model = hourly.conditionIcon.withProtocol(),
            contentDescription = hourly.condition,
            modifier = Modifier.size(dimens.iconSizeLarge)
        )
        Spacer(modifier = Modifier.height(dimens.spacingSmall))
        Text(
            text = stringResource(R.string.temperature_degrees, hourly.temperature.toInt()),
            style = typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
