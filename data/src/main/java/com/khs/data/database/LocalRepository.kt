package com.khs.data.database

import com.khs.domain.database.LocalGateway
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.util.Utils.asFlowResult
import kotlinx.coroutines.flow.Flow
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
    override suspend fun updateSeasonDB(seasonList: List<String>) {
//        val seasonList : ArrayList<SeasonEntity> = arrayListOf()
//        for (item in 0 until stringList.size step 3) {
//            val loIndex = index
//            val seasonId = stringList[index]
//            val className = stringList[++index]
//            val seasonImg = stringList[++index]
//            seasonList.add(SeasonEntity(null, seasonId.toLong(), className, seasonImg))
//            index++
//        }
//        val startTime = System.currentTimeMillis()
//        val localPlayerList = seasonDao.getAll()
//        if(seasonList.size != localPlayerList.size) {
//            index = 0
//            seasonDao.deleteAll()
//            for (item in stringList.indices step 3) {
//                val loIndex = index
//                val seasonId = stringList[index]
//                val className = stringList[++index]
//                val seasonImg = stringList[++index]
//                seasonDao.insert(SeasonEntity(null, seasonId.toLong(), className, seasonImg))
//                index++
//            }
//        }
    }

    override suspend fun getAllPlayer() : Flow<CommonResult<List<Player>>> = asFlowResult { playerDao.getAll().asPlayers() }
    override suspend fun getPlayer(playerId: String) : Flow<CommonResult<Player?>> = asFlowResult { playerDao.getPlayer(playerId)?.asPlayer() ?: Player(null, null, null)}
    override suspend fun deleteAllPlayer() = playerDao.deleteAll()
    override suspend fun updatePlayerDB(playerList: List<String>) {

    }
}