package com.khs.domain.database.usecase

import com.khs.domain.database.LocalRepository
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.datastore.DataStoreRepository
import com.khs.domain.nexon.entity.CommonResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSetupUseCase @Inject constructor(
    private val localGateway: LocalRepository,
    private val dataStoreGateway: DataStoreRepository
) {
    suspend fun getSeasonIds(): Flow<CommonResult<List<Season>>> = localGateway.getAllSeason()
    suspend fun getPlayerNames(): Flow<CommonResult<List<Player>>> = localGateway.getAllPlayer()
    suspend fun updatePlayerDB(playerList: List<Player>) = localGateway.updatePlayerDB(playerList)
    suspend fun updateSeasonDB(seasonList: List<Season>) = localGateway.updateSeasonDB(seasonList)


    suspend fun getPlayerDBUpdateTime(): Flow<Long> = dataStoreGateway.getPlayerUpdateTime()
    suspend fun editPlayerUpdateTime(timeMs: Long) = dataStoreGateway.editPlayerUpdateTime(timeMs = timeMs)
    suspend fun getSeasonDBUpdateTime(): Flow<Long> = dataStoreGateway.getSeasonUpdateTime()
    suspend fun editSeasonUpdateTime(timeMs: Long) = dataStoreGateway.editSeasonUpdateTime(timeMs = timeMs)
}