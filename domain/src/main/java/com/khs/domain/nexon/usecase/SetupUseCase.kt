package com.khs.domain.nexon.usecase

import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.NexonAPIGateway
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class SetupUseCase @Inject constructor(
    private val nexonAPIGateway: NexonAPIGateway
) {

    suspend fun getSeasonIds(): Flow<CommonResult<ResponseBody>> {
        return nexonAPIGateway.getSeasonIds()
    }

    suspend fun getPlayerNames(): Flow<CommonResult<ResponseBody>> {
        return nexonAPIGateway.getPlayerName()
    }
}