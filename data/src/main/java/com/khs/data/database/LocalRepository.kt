package com.khs.data.database

import com.khs.data.database.entity.PlayerEntity
import com.khs.data.database.entity.SeasonEntity
import com.khs.domain.database.LocalGateway
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.util.Utils.asFlowResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val seasonDao: SeasonDao,
    private val playerDao: PlayerDao
) : LocalGateway {
    override suspend fun getAllSeason() : Flow<CommonResult<List<Season>>> = asFlowResult { seasonDao.getAll().asSeasons() }
    override suspend fun getSeason(seasonId: String) : Flow<CommonResult<Season>> = asFlowResult { seasonDao.getSeason(seasonId).asSeason() }
    override suspend fun deleteAllSeason() = seasonDao.deleteAll()

    /* TODO 23.08.17
    - MVP -> MVVM 전환
    - 앱 초기 DB 셋팅 로직 개선(선수 이름 json 파일 내재화)
    - 넥슨 API 각 Usecase 별 구현
    * */
    override suspend fun updateSeasonDB(seasonList: List<Season>) {
        val savedSeasonList = seasonDao.getAll()
        if(seasonList.size != savedSeasonList.size) {
            val seasonEntityList: List<SeasonEntity> = seasonList.map { season ->
                SeasonEntity(id = null, seasonId = season.seasonId, seasonImg = season.seasonImg, className = season.className)
            }
            val deleteJob = CoroutineScope(Dispatchers.Default).launch {
                seasonDao.deleteAll()
            }
            deleteJob.join()
            seasonEntityList.forEach {
                seasonDao.insert(it)
            }
        }
    }

    override suspend fun getAllPlayer() : Flow<CommonResult<List<Player>>> = asFlowResult { playerDao.getAll().asPlayers() }
    override suspend fun getPlayer(playerId: String) : Flow<CommonResult<Player?>> = asFlowResult { playerDao.getPlayer(playerId)?.asPlayer() ?: Player(null, null, null)}
    override suspend fun deleteAllPlayer() = playerDao.deleteAll()
    override suspend fun updatePlayerDB(playerList: List<Player>) {
        val savedPlayerList = playerDao.getAll()
        if (playerList.size != savedPlayerList.size) {
            val playerEntityList = playerList.map {
                PlayerEntity(id = null, playerId = it.playerId, playerName = it.playerName)
            }
            val deleteJob = CoroutineScope(Dispatchers.Default).launch {
                playerDao.deleteAll()
            }
            deleteJob.join()
            playerEntityList.forEach {
                playerDao.insert(it)
            }
        }
    }
}