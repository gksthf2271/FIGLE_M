package com.khs.data.database

import android.util.Log
import com.khs.data.database.entity.PlayerEntity
import com.khs.data.database.entity.SeasonEntity
import com.khs.domain.database.LocalRepository
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.util.Utils.asFlowResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val seasonDao: SeasonDao,
    private val playerDao: PlayerDao
) : LocalRepository {
    override suspend fun getAllSeason() : Flow<CommonResult<List<Season>>> = asFlowResult { seasonDao.getAll().asSeasons() }
    override suspend fun getSeason(seasonId: String) : Flow<CommonResult<Season>> = asFlowResult { seasonDao.getSeason(seasonId).asSeason() }
    override suspend fun deleteAllSeason() = seasonDao.deleteAll()
    override suspend fun updateSeasonDB(seasonList: List<Season>) {
        val deleteJob = CoroutineScope(Dispatchers.Default).launch {
            seasonDao.deleteAll()
        }
        deleteJob.join()
        val startTime = System.currentTimeMillis()
        CoroutineScope(Dispatchers.Default).launch {
            seasonList.map { season ->
                SeasonEntity(id = null, seasonId = season.seasonId, seasonImg = season.seasonImg, className = season.className)
            }.run {
                seasonDao.insertList(this)
                Log.d("KHS","Season DB input Completed! : ${seasonList.size} / ${System.currentTimeMillis()-startTime}ms")
            }
        }
    }

    override suspend fun getAllPlayer() : Flow<CommonResult<List<Player>>> = asFlowResult { playerDao.getAll().asPlayers() }
    override suspend fun getPlayer(playerId: String) : Flow<CommonResult<Player?>> = asFlowResult { playerDao.getPlayer(playerId)?.asPlayer() ?: Player(null, null, null)}
    override suspend fun deleteAllPlayer() = playerDao.deleteAll()
    override suspend fun updatePlayerDB(playerList: List<Player>) {
        val deleteJob = CoroutineScope(Dispatchers.Default).launch {
            playerDao.deleteAll()
        }
        deleteJob.join()
        Log.d("KHS","Player DB delete Completed!")
        val startTime = System.currentTimeMillis()
        CoroutineScope(Dispatchers.Default).launch {
            playerList.map {
                PlayerEntity(id = null, playerId = it.playerId, playerName = it.playerName)
            }.run {
                playerDao.insertList(this)
            }
            Log.d("KHS","Player DB input Completed! : ${playerList.size} / ${System.currentTimeMillis()-startTime}ms")
        }
    }
}