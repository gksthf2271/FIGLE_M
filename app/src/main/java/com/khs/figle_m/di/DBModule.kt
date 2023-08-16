package com.khs.figle_m.di

import android.content.Context
import androidx.room.Room
import com.khs.data.database.PlayerDao
import com.khs.data.database.PlayerDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun providePlayerDatabase(
        @ApplicationContext context: Context
    ): PlayerDataBase = Room
        .databaseBuilder(context, PlayerDataBase::class.java, "Player.db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideAlbumDao(playerDataBase: PlayerDataBase): PlayerDao = playerDataBase.playerDao()
}