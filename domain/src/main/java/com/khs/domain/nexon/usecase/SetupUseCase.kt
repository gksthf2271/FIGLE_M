package com.khs.domain.nexon.usecase

import com.khs.domain.database.entity.PlayerData
import com.khs.domain.database.entity.SeasonData
import com.khs.domain.nexon.NexonAPIGateway
import com.khs.domain.nexon.entity.CommonResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetupUseCase @Inject constructor(
    private val nexonAPIGateway: NexonAPIGateway
) {
    suspend fun getSeasonList(): Flow<CommonResult<SeasonData>> = nexonAPIGateway.getSeasonList()

    suspend fun getPlayerNameList(): Flow<CommonResult<PlayerData>> = nexonAPIGateway.getPlayerNameList()
}