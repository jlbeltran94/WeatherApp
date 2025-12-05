package com.jlbeltran94.weatherapp.presentation.screens.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun NoNetworkScreen(
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimens.paddingLarge)
            .testTag(TestTags.NO_NETWORK_SCREEN),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_no_internet),
                contentDescription = null,
                modifier = Modifier.size(dimens.iconSizeLarge)
            )

            Spacer(modifier = Modifier.padding(dimens.spacingLarge))

            Text(
                text = stringResource(id = R.string.no_network_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = dimens.paddingMedium),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.no_network_description),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = dimens.paddingLarge),
                textAlign = TextAlign.Center
            )

            TextButton(
                onClick = onRetry,
                modifier = Modifier.padding(horizontal = dimens.paddingLarge)
            ) {
                Text(stringResource(id = R.string.ok))
            }
        }
    }
}
