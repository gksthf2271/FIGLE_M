package com.khs.data.database

import androidx.room.Dao
import androidx.room.Query
import com.khs.data.database.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonDao : BaseDAO<SeasonEntity>{
    // Example)
    //    "SELECT * FROM AD WHERE Canceled = :mIsCancelled AND Type = :mType AND Status = :mStatus"
    @Query("SELECT * FROM Season")
    fun getAll(): List<SeasonEntity>

    @Query("SELECT * FROM Season WHERE seasonId = :seasonId")
    suspend fun getSeason(seasonId:String): SeasonEntity

    @Query("DELETE from Season")
    suspend fun deleteAll()
}