package com.khs.data.database

import androidx.room.Dao
import androidx.room.Query
import com.khs.data.database.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao : BaseDAO<PlayerEntity>{
    @Query("SELECT * FROM Player")
    suspend fun getAll(): Flow<List<PlayerEntity>>

//    "SELECT * FROM AD WHERE Canceled = :mIsCancelled AND Type = :mType AND Status = :mStatus"
    @Query("SELECT * FROM Player WHERE playerId = :playerId")
    suspend fun getPlayer(playerId:String): Flow<PlayerEntity?>

    @Query("DELETE from player")
    suspend fun deleteAll()
}