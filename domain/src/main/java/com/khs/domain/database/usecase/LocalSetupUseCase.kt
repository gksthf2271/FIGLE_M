package com.khs.domain.database.usecase

import com.khs.domain.database.LocalGateway
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class LocalSetupUseCase @Inject constructor(
    private val localGateway: LocalGateway
) {

    suspend fun getSeasonIds(): Flow<CommonResult<List<Season>>> = localGateway.getAllSeason()

    suspend fun getPlayerNames(): Flow<CommonResult<List<Player>>> = localGateway.getAllPlayer()

    suspend fun updatePlayerDB(responseBody: ResponseBody) {}

    suspend fun updateSeasonDB(responseBody: ResponseBody) {}
}