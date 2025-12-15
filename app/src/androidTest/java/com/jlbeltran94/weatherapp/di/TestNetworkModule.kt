package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.commonnetwork.di.NetworkModule
import com.jlbeltran94.weatherdetailcomponent.data.remote.WeatherApiService
import com.jlbeltran94.weatherdetailcomponent.di.NetworkModule as WeatherNetworkModule
import com.jlbeltran94.searchlocationcomponent.di.NetworkModule as SearchNetworkModule
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
    replaces = [NetworkModule::class, WeatherNetworkModule::class, SearchNetworkModule::class]
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
