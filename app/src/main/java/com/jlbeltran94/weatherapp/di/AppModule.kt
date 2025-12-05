package com.jlbeltran94.weatherapp.di

import com.jlbeltran94.weatherapp.data.util.NetworkMonitorImpl
import com.jlbeltran94.weatherapp.domain.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindNetworkMonitor(impl: NetworkMonitorImpl): NetworkMonitor
}
