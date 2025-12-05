package com.jlbeltran94.weatherapp.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Creates a reusable shimmer effect brush.
 * @param shimmerColor The base color for the shimmer effect.
 * @return A [Brush] that can be used as a background for shimmer animations.
 */
@Composable
fun rememberShimmerBrush(shimmerColor: Color = Color.LightGray): Brush {
    val shimmerColors = listOf(
        shimmerColor.copy(alpha = 0.9f),
        shimmerColor.copy(alpha = 0.4f),
        shimmerColor.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer_transition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f, // A large value to ensure the gradient covers the screen
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate_animation"
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
}
