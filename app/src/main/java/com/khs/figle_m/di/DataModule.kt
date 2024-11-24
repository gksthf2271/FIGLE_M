package com.khs.figle_m.di

import com.khs.data.database.LocalRepositoryImpl
import com.khs.data.datastore.DataStoreRepositoryImpl
import com.khs.data.nexon_api.NexonAPIRepository
import com.khs.domain.database.LocalRepository
import com.khs.domain.datastore.DataStoreRepository
import com.khs.domain.nexon.NexonAPIGateway
import com.khs.figle_m.core.ConnectivityManagerNetworkMonitor
import com.khs.figle_m.core.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindsNexonAPIRepository(
        nexonApiRepository: NexonAPIRepository
    ): NexonAPIGateway

    @Binds
    @Singleton
    fun bindsLocalRepository(
        localRepository: LocalRepositoryImpl
    ) : LocalRepository

    @Binds
    @Singleton
    fun bindsDataStoreRepository(
        dataStoreRepository: DataStoreRepositoryImpl
    ) : DataStoreRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}