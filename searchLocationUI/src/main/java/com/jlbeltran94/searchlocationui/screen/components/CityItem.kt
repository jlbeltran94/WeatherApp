package com.jlbeltran94.searchlocationui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.jlbeltran94.commonui.theme.AppTheme.dimens
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationui.R

@Composable
fun CitiesResults(
    cities: List<City>,
    keyboardController: SoftwareKeyboardController?,
    onNavigateToDetail: (String) -> Unit
) {
    LazyColumn {
        items(cities) { city ->
            CityItem(
                city = city,
                onClick = {
                    keyboardController?.hide()
                    onNavigateToDetail("${city.name},${city.country}")
                }
            )
        }
    }
}

@Composable
fun CityItem(
    city: City,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.paddingSmall)
            .clickable(onClick = onClick)
            .testTag("TestTags.CITY_ITEM"),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.spacingSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.paddingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = null,
                modifier = Modifier.size(dimens.iconSizeMedium)
            )
            Spacer(modifier = Modifier.size(dimens.spacingLarge))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = city.name,
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(
                        id = R.string.city_region_country,
                        city.region,
                        city.country
                    ),
                    style = typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(dimens.iconSizeSmall)
            )
        }
    }
}
