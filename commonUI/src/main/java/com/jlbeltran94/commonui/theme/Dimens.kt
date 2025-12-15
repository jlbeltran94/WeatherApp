package com.jlbeltran94.commonui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimens(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingMediumLarge: Dp = 12.dp,
    val paddingLarge: Dp = 16.dp,
    val paddingExtraLarge: Dp = 32.dp,

    val spacingSmall: Dp = 4.dp,
    val spacingMedium: Dp = 8.dp,
    val spacingMediumLarge: Dp = 12.dp,
    val spacingLarge: Dp = 16.dp,
    val spacingExtraLarge: Dp = 24.dp,

    val iconSizeSmall: Dp = 24.dp,
    val iconSizeMedium: Dp = 32.dp,
    val iconSizeMediumLarge: Dp = 36.dp,
    val iconSizeLarge: Dp = 48.dp,
    val iconSizeExtraLarge: Dp = 64.dp,

    val splashIconSize: Dp = 200.dp,

    val fontSmall: TextUnit = 12.sp,
    val fontMedium: TextUnit = 18.sp,
    val fontLarge: TextUnit = 48.sp,

    val toolbarHeight: Dp = 80.dp,
    val detailHeaderHeight: Dp = 200.dp,

    val hourlyItemWidth: Dp = 70.dp,
    val dailyItemDayWidth: Dp = 100.dp,
    val dailyItemTempWidth: Dp = 40.dp,

    // Shimmer dimensions
    val shimmerToolbarSpacer: Dp = 64.dp,
    val shimmerTitleWidth: Dp = 150.dp,
    val shimmerConditionHeight: Dp = 30.dp,
    val shimmerConditionWidth: Dp = 200.dp,
    val shimmerTempHeight: Dp = 24.dp,
    val shimmerTempWidth: Dp = 120.dp,
    val shimmerCardHeightSmall: Dp = 100.dp,
    val shimmerCardHeightMedium: Dp = 150.dp,
    val shimmerCardHeightLarge: Dp = 250.dp,
    val shimmerSearchItemHeight: Dp = 20.dp,

    val elevationSmall: Dp = 2.dp
)

val LocalDimens = compositionLocalOf { Dimens() }
