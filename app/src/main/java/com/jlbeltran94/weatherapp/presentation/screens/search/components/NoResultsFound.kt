package com.jlbeltran94.weatherapp.presentation.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jlbeltran94.weatherapp.R
import com.jlbeltran94.weatherapp.presentation.theme.AppTheme.dimens
import com.jlbeltran94.weatherapp.presentation.util.TestTags

@Composable
fun NoResultsFound() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.NO_RESULTS_FOUND),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimens.paddingLarge)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_no_result),
                contentDescription = null,
                modifier = Modifier.size(dimens.iconSizeLarge)
            )
            Spacer(modifier = Modifier.height(dimens.spacingLarge))
            Text(
                text = stringResource(R.string.no_cities_found),
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dimens.spacingMedium))
            Text(
                text = stringResource(R.string.no_cities_found_description),
                style = typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
