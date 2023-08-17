package com.khs.data.database

import com.khs.data.database.entity.PlayerEntity
import com.khs.data.database.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val seasonDao: SeasonDao,
    private val playerDao: PlayerDao
) {
    suspend fun getAllSeason() : Flow<List<SeasonEntity>> = seasonDao.getAll()
    suspend fun getSeason(seasonId: String) : Flow<SeasonEntity> = seasonDao.getSeason(seasonId)
    suspend fun deleteAllSeason() = seasonDao.deleteAll()

    suspend fun getAllPlayer() : Flow<List<PlayerEntity>> = playerDao.getAll()
    suspend fun getPlayer(playerId: String) : Flow<PlayerEntity?> = playerDao.getPlayer(playerId)
    suspend fun deleteAllPlayer() = playerDao.deleteAll()
}