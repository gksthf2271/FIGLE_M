package com.khs.domain.nexon.usecase

import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.NexonAPIGateway
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetupUseCase @Inject constructor(
    private val nexonAPIGateway: NexonAPIGateway
) {

    suspend fun getSeasonList(): Flow<CommonResult<List<Season>>> {
        return nexonAPIGateway.getSeasonList()
    }

    suspend fun getPlayerNameList(): Flow<CommonResult<List<Player>>> {
        return nexonAPIGateway.getPlayerNameList()
    }
}