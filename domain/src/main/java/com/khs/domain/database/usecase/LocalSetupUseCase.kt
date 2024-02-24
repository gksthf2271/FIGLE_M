package com.khs.domain.database.usecase

import com.khs.domain.database.LocalGateway
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSetupUseCase @Inject constructor(
    private val localGateway: LocalGateway
) {

    suspend fun getSeasonIds(): Flow<CommonResult<List<Season>>> = localGateway.getAllSeason()

    suspend fun getPlayerNames(): Flow<CommonResult<List<Player>>> = localGateway.getAllPlayer()

    suspend fun updatePlayerDB(playerList: List<Player>) = localGateway.updatePlayerDB(playerList)

    suspend fun updateSeasonDB(seasonList: List<Season>) = localGateway.updateSeasonDB(seasonList)
}