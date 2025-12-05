package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideApiKey(): String {
        return "WEATHER_API_KEY"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = mockk()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = mockk()

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService = mockk()
}
