package com.jlbeltran94.commonnetwork.di

import android.os.Build
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jlbeltran94.commonnetwork.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("ApiKey")
    fun provideApiKey(): String {
        return BuildConfig.WEATHER_API_KEY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("authInterceptor") authInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }
        return builder
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val contentType = "application/json".toMediaType()
        val baseUrl = BuildConfig.WEATHER_API_URL.withProtocol()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @Named("authInterceptor")
    fun provideAuthInterceptor(@Named("ApiKey") apiKey: String): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val url = originalRequest.url.newBuilder()
                .addQueryParameter("key", apiKey)
                .build()
            val newRequest = originalRequest.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }
    }

}

fun String.withProtocol(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        "https:$this"
    } else {
        "http:$this"
    }
}
