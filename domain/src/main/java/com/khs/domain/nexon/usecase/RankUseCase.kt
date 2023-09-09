package com.khs.domain.nexon.usecase

import com.khs.domain.nexon.NexonAPIGateway
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RankUseCase @Inject constructor(
    private val nexonAPIGateway: NexonAPIGateway
) {
    suspend fun getRank(page: Int): Flow<CommonResult<ResponseBody>> {
        return nexonAPIGateway.getRank(page = page)
    }
}