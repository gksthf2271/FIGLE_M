package com.khs.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khs.data.database.entity.PlayerEntity
import com.khs.data.database.entity.SeasonEntity

@Database(entities = [PlayerEntity::class, SeasonEntity::class], version = 2)
abstract class PlayerDataBase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun seasonDao(): SeasonDao

    companion object {
        private var INSTANCE: PlayerDataBase? = null

        fun getInstance(context: Context): PlayerDataBase? {
            if (INSTANCE == null) {
                synchronized(PlayerDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PlayerDataBase::class.java, "Player.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}