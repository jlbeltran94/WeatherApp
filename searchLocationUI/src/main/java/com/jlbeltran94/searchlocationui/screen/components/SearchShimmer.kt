package com.jlbeltran94.searchlocationui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import com.jlbeltran94.commonui.components.rememberShimmerBrush
import com.jlbeltran94.commonui.theme.AppTheme.dimens

@Composable
fun SearchShimmer() {
    val brush = rememberShimmerBrush()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("TestTags.SEARCH_SHIMMER")
    ) {
        items(8) {
            ShimmerCityItem(brush = brush)
        }
    }
}

@Composable
private fun ShimmerCityItem(brush: Brush) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.paddingSmall),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.elevationSmall),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.paddingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(dimens.iconSizeLarge)
                    .background(brush, shape = RoundedCornerShape(dimens.spacingMedium))
            )
            Spacer(modifier = Modifier.size(dimens.spacingLarge))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(dimens.shimmerSearchItemHeight)
                        .fillMaxWidth(0.7f)
                        .background(brush, shape = RoundedCornerShape(dimens.spacingSmall))
                )
                Spacer(
                    modifier = Modifier
                        .height(dimens.shimmerSearchItemHeight)
                        .fillMaxWidth(0.5f)
                        .background(brush, shape = RoundedCornerShape(dimens.spacingSmall))
                )
            }
            Spacer(
                modifier = Modifier
                    .size(dimens.iconSizeSmall)
                    .background(brush, shape = RoundedCornerShape(dimens.spacingSmall))
            )
        }
    }
}
