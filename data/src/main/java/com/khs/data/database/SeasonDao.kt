package com.khs.data.database

import androidx.room.Dao
import androidx.room.Query
import com.khs.data.database.entity.SeasonEntity

@Dao
interface SeasonDao : BaseDAO<SeasonEntity>{
    // Example)
    //    "SELECT * FROM AD WHERE Canceled = :mIsCancelled AND Type = :mType AND Status = :mStatus"
    @Query("SELECT * FROM Season")
    fun getAll(): List<SeasonEntity>

    @Query("SELECT * FROM Season WHERE seasonId = :seasonId")
    fun getSeason(seasonId:String): SeasonEntity

    @Query("DELETE from Season")
    fun deleteAll()
}