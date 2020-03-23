package com.example.figle_m.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayerEntity::class], version = 1)
abstract class PlayerDataBase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao

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