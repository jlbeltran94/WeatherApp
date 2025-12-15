package com.jlbeltran94.commonnetwork.di

import com.jlbeltran94.commonnetwork.util.NetworkMonitor
import com.jlbeltran94.commonnetwork.util.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    @Binds
    abstract fun bindNetworkMonitor(impl: NetworkMonitorImpl): NetworkMonitor
}
