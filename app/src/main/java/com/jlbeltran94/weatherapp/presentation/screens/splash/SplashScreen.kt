package com.jlbeltran94.weatherapp.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jlbeltran94.commonui.ErrorType
import com.jlbeltran94.commonui.theme.AppTheme.dimens
import com.jlbeltran94.commonui.theme.DarkBlue
import com.jlbeltran94.commonui.theme.SkyBlue
import com.jlbeltran94.commonui.theme.White
import com.jlbeltran94.weatherapp.BuildConfig
import com.jlbeltran94.weatherapp.R
import com.jlbeltran94.weatherapp.presentation.util.TestTags

@Composable
fun SplashScreen(
    uiState: SplashUiState,
    onNavigateToSearch: () -> Unit,
    onNavigateToError: (ErrorType) -> Unit,
) {
    when (uiState) {
        is SplashUiState.Success -> onNavigateToSearch()
        is SplashUiState.Error -> onNavigateToError(uiState.errorType)
        SplashUiState.Loading -> {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.weather_splash_animation))

            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colors = listOf(SkyBlue, DarkBlue)))
                        .testTag(TestTags.SPLASH_LOADING),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(dimens.splashIconSize),
                            speed = 2F
                        )

                        Text(
                            text = stringResource(R.string.splash_app_name),
                            fontSize = dimens.fontLarge,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )

                        Text(
                            text = stringResource(R.string.splash_tagline),
                            fontSize = dimens.fontMedium,
                            color = White,
                            modifier = Modifier.padding(top = dimens.paddingMedium)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = stringResource(
                                R.string.splash_version,
                                BuildConfig.VERSION_NAME
                            ),
                            fontSize = dimens.fontSmall,
                            color = White,
                        )
                    }
                }
            }
        }
    }
}
