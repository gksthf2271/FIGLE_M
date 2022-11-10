package com.khs.figle_m.DB

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlayerDao : BaseDAO<PlayerEntity>{
    @Query("SELECT * FROM Player")
    fun getAll(): List<PlayerEntity>

//    "SELECT * FROM AD WHERE Canceled = :mIsCancelled AND Type = :mType AND Status = :mStatus"
    @Query("SELECT * FROM Player WHERE playerId = :playerId")
    fun getPlayer(playerId:String): PlayerEntity?

    @Query("DELETE from player")
    fun deleteAll()
}