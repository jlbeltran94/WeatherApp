package com.jlbeltran94.weatherapp.presentation.screens.detail.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jlbeltran94.weatherapp.presentation.components.rememberShimmerBrush
import com.jlbeltran94.weatherapp.presentation.theme.AppTheme.dimens
import com.jlbeltran94.weatherapp.presentation.theme.DarkBlue
import com.jlbeltran94.weatherapp.presentation.theme.SkyBlue

@Composable
fun WeatherDetailShimmer() {
    val shimmerBrush = rememberShimmerBrush()

    Column(modifier = Modifier.fillMaxSize()) {
        // Top section shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.detailHeaderHeight)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(SkyBlue.copy(alpha = 0.6f), DarkBlue.copy(alpha = 0.6f))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.paddingLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(dimens.shimmerToolbarSpacer)) // For Toolbar
                Spacer(
                    modifier = Modifier
                        .height(dimens.toolbarHeight)
                        .width(dimens.shimmerTitleWidth)
                        .background(shimmerBrush, RoundedCornerShape(dimens.spacingMediumLarge))
                )
                Spacer(modifier = Modifier.height(dimens.spacingLarge))
                Spacer(
                    modifier = Modifier
                        .height(dimens.shimmerConditionHeight)
                        .width(dimens.shimmerConditionWidth)
                        .background(shimmerBrush, RoundedCornerShape(dimens.spacingMedium))
                )
                Spacer(modifier = Modifier.height(dimens.spacingLarge))
                Spacer(
                    modifier = Modifier
                        .height(dimens.shimmerTempHeight)
                        .width(dimens.shimmerTempWidth)
                        .background(shimmerBrush, RoundedCornerShape(dimens.spacingMedium))
                )
            }
        }

        // Bottom section shimmer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingLarge)
        ) {
            // Hourly Forecast Card Shimmer
            ShimmerCard(height = dimens.shimmerCardHeightMedium, brush = shimmerBrush)

            // 7-Day Forecast Card Shimmer
            ShimmerCard(height = dimens.shimmerCardHeightLarge, brush = shimmerBrush)

            // Detail Cards Shimmer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingLarge)
            ) {
                ShimmerCard(
                    modifier = Modifier.weight(1f),
                    height = dimens.shimmerCardHeightSmall,
                    brush = shimmerBrush
                )
                ShimmerCard(
                    modifier = Modifier.weight(1f),
                    height = dimens.shimmerCardHeightSmall,
                    brush = shimmerBrush
                )
            }
        }
    }
}

@Composable
private fun ShimmerCard(modifier: Modifier = Modifier, height: Dp, brush: Brush) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(dimens.spacingLarge),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        )
    }
}
