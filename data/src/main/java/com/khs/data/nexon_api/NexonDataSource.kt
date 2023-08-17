package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.asHighRanker
import com.khs.data.nexon_api.response.asImageByteArray
import com.khs.data.nexon_api.response.asMatch
import com.khs.data.nexon_api.response.asMatchIds
import com.khs.data.nexon_api.response.asRankerPlayers
import com.khs.data.nexon_api.response.asTradeInfo
import com.khs.data.nexon_api.response.asUser
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.User
import com.khs.domain.nexon.entity.HighRankUser
import com.khs.domain.nexon.entity.RankerPlayer
import com.khs.domain.nexon.entity.TradeInfo
import com.khs.domain.util.Utils.asFlowResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class NexonDataSource@Inject constructor(
    private val apiKey: String,
    private val nexonService: NexonService) {

    suspend fun searchUser(nickname: String): Flow<CommonResult<User>> =
        asFlowResult { nexonService.requestUser(authorization = apiKey, nickname = nickname).asUser() }

    suspend fun getMatchDetail(matchId: String): Flow<CommonResult<Match>> =
        asFlowResult { nexonService.requestMatchDetail(authorization = apiKey, matchid = matchId).asMatch() }

    suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonResult<List<String>>> = asFlowResult {
        nexonService.requestMatchIds(authorization = apiKey, accessid = accessId, matchtype = matchType, offset = offset, limit = limit).asMatchIds()
    }

    suspend fun getHighRankUser(
        accessId: String
    ) : Flow<CommonResult<List<HighRankUser>>> = asFlowResult {
        nexonService.requestHighRanker(authorization = apiKey, accessid = accessId).asHighRanker()
    }

    suspend fun getTradeInfo(
        accessId: String,
        tradeType : String,
        offset: Int?,
        limit: Int?
    ) : Flow<CommonResult<List<TradeInfo>>> = asFlowResult {
        nexonService.requestTradeInfo(authorization = apiKey, accessid = accessId, tradeType = tradeType, offset = offset, limit = limit).asTradeInfo()
    }

    suspend fun getPlayerImg(
        spId: Int
    ) : Flow<CommonResult<ByteArray>> = asFlowResult {
        nexonService.requestPlayerImage(authorization = apiKey, spid = spId).asImageByteArray()
    }

    suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ) : Flow<CommonResult<List<RankerPlayer>>> = asFlowResult {
        nexonService.requestRankerPlayerAverList(authorization = apiKey, matchType = matchType, players = players).asRankerPlayers()
    }

    suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ) : Flow<CommonResult<ResponseBody>> = asFlowResult {
        nexonService.requestPlayerInfo(spId = spId, strong = n1Strong)
    }

    suspend fun getRank(
        page : Int
    ) : Flow<CommonResult<ResponseBody>> = asFlowResult {
        nexonService.requestRank(page = page)
    }

    suspend fun getPlayerName() : Flow<CommonResult<ResponseBody>> =
        asFlowResult { nexonService.requestPlayerName(authorization = apiKey) }

    suspend fun getSeasonIds() : Flow<CommonResult<ResponseBody>> =
        asFlowResult { nexonService.requestSeasonIdList(authorization = apiKey) }
}