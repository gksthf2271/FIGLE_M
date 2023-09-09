package com.khs.figle_m.di

import com.khs.data.database.LocalRepository
import com.khs.data.nexon_api.NexonAPIRepository
import com.khs.domain.database.LocalGateway
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
        localRepository: LocalRepository
    ) : LocalGateway
}