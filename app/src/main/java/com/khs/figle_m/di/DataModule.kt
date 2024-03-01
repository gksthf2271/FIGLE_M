package com.khs.figle_m.di

import com.khs.data.database.LocalRepositoryImpl
import com.khs.data.datastore.DataStoreRepositoryImpl
import com.khs.data.nexon_api.NexonAPIRepository
import com.khs.domain.database.LocalRepository
import com.khs.domain.datastore.DataStoreRepository
import com.khs.domain.nexon.NexonAPIGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindsNexonAPIRepository(
        nexonApiRepository: NexonAPIRepository
    ): NexonAPIGateway

    @Binds
    @Singleton
    abstract fun bindsLocalRepository(
        localRepository: LocalRepositoryImpl
    ) : LocalRepository

    @Binds
    @Singleton
    abstract fun bindsDataStoreRepository(
        dataStoreRepository: DataStoreRepositoryImpl
    ) : DataStoreRepository
}