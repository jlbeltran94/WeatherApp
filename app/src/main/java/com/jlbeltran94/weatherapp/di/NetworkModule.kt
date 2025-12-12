package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.data.remote.CityApiService
import com.jlbeltran94.weatherapp.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Provides
//    @Singleton
//    fun provideApiKey(): String {
//        return BuildConfig.WEATHER_API_KEY
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        val json = Json {
//            ignoreUnknownKeys = true
//            isLenient = true
//        }
//        val contentType = "application/json".toMediaType()
//        val baseUrl = BuildConfig.WEATHER_API_URL.withProtocol()
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .client(okHttpClient)
//            .addConverterFactory(json.asConverterFactory(contentType))
//            .build()
//    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCityApiService(retrofit: Retrofit): CityApiService {
        return retrofit.create(CityApiService::class.java)
    }
}
