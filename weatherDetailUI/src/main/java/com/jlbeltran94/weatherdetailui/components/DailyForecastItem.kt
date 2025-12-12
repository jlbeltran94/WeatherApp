package com.jlbeltran94.weatherdetailui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.jlbeltran94.commonnetwork.di.withProtocol
import com.jlbeltran94.commonui.theme.AppTheme.dimens
import com.jlbeltran94.weatherdetailcomponent.domain.model.DailyForecast
import com.jlbeltran94.weatherdetailcomponent.domain.model.Weather
import com.jlbeltran94.weatherdetailui.R

@Composable
fun DailyForecastList(
    weather: Weather,
) {
    if (!weather.dailyForecast.isNullOrEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimens.spacingLarge),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(dimens.paddingLarge)) {
                Text(
                    text = stringResource(R.string.seven_day_forecast),
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = dimens.paddingMediumLarge)
                )
                weather.dailyForecast?.take(7)?.forEach { daily ->
                    DailyForecastItem(daily)
                    Spacer(modifier = Modifier.height(dimens.spacingMediumLarge))
                }
            }
        }
    }
}

@Composable
fun DailyForecastItem(daily: DailyForecast) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = daily.day,
            style = typography.bodyLarge,
            modifier = Modifier.width(dimens.dailyItemDayWidth)
        )
        AsyncImage(
            model = daily.conditionIcon.withProtocol(),
            contentDescription = daily.condition,
            modifier = Modifier.size(dimens.iconSizeLarge)
        )
        Text(
            text = stringResource(R.string.temperature_degrees, daily.lowTemp.toInt()),
            style = typography.bodyMedium,
            color = colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.width(dimens.dailyItemTempWidth),
            textAlign = TextAlign.End
        )
        TemperatureBar(
            modifier = Modifier
                .weight(1f)
                .height(dimens.spacingMedium)
                .padding(horizontal = dimens.paddingMedium)
        )
        Text(
            text = stringResource(R.string.temperature_degrees, daily.highTemp.toInt()),
            style = typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(dimens.dailyItemTempWidth)
        )
    }
}
