package com.jlbeltran94.weatherapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbar(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    toolbar: @Composable (collapseFraction: Float) -> Unit,
    headerHeight: Dp,
    toolbarHeight: Dp,
) {
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    var offset by remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = offset + delta
                offset = newOffset.coerceIn(-(headerHeightPx - toolbarHeightPx), 0f)
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Header with Parallax Effect (behind the Scaffold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .graphicsLayer { translationY = offset * 0.5f } // Parallax
        ) {
            header()
        }

        // Scaffold contains the Toolbar and the Scrollable content
        Scaffold(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            topBar = {
                val collapseFraction = (kotlin.math.abs(offset) / (headerHeightPx - toolbarHeightPx)).coerceIn(0f, 1f)
                toolbar(collapseFraction)
            },
            containerColor = Color.Transparent // Make scaffold transparent
        ) { contentPadding ->
            LazyColumn(
                modifier = Modifier.padding(contentPadding)
            ) {
                // Spacer to push the content below the initial header position
                item {
                    Spacer(modifier = Modifier.height(headerHeight - toolbarHeight))
                }
                item {
                    content()
                }
            }
        }
    }
}
