package com.jlbeltran94.weatherapp.presentation.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.jlbeltran94.weatherapp.presentation.theme.AppTheme.dimens

@Composable
fun TemperatureBar(
    modifier: Modifier = Modifier,
    minColor: Color = Color(0xFF4A90E2),
    maxColor: Color = Color(0xFFFFA500)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.spacingSmall))
            .background(Brush.horizontalGradient(colors = listOf(minColor, maxColor)))
    )
}
