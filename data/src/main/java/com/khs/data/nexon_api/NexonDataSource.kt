package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.asHighRanker
import com.khs.data.nexon_api.response.asImageByteArray
import com.khs.data.nexon_api.response.asMatch
import com.khs.data.nexon_api.response.asMatchIds
import com.khs.data.nexon_api.response.asRankerPlayers
import com.khs.data.nexon_api.response.asTradeInfo
import com.khs.data.nexon_api.response.asUser
import com.khs.domain.entity.CommonApiResult
import com.khs.domain.entity.Match
import com.khs.domain.entity.User
import com.khs.domain.entity.HighRankUser
import com.khs.domain.entity.RankerPlayer
import com.khs.domain.entity.TradeInfo
import com.khs.domain.util.Utils.asFlowApiResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class NexonDataSource@Inject constructor(
    private val apiKey: String,
    private val nexonService: NexonService) {

    suspend fun searchUser(nickname: String): Flow<CommonApiResult<User>> =
        asFlowApiResult { nexonService.requestUser(authorization = apiKey, nickname = nickname).asUser() }

    suspend fun getMatchDetail(matchId: String): Flow<CommonApiResult<Match>> =
        asFlowApiResult { nexonService.requestMatchDetail(authorization = apiKey, matchid = matchId).asMatch() }

    suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonApiResult<List<String>>> = asFlowApiResult {
        nexonService.requestMatchIds(authorization = apiKey, accessid = accessId, matchtype = matchType, offset = offset, limit = limit).asMatchIds()
    }

    suspend fun getHighRankUser(
        accessId: String
    ) : Flow<CommonApiResult<List<HighRankUser>>> = asFlowApiResult {
        nexonService.requestHighRanker(authorization = apiKey, accessid = accessId).asHighRanker()
    }

    suspend fun getTradeInfo(
        accessId: String,
        tradeType : String,
        offset: Int?,
        limit: Int?
    ) : Flow<CommonApiResult<List<TradeInfo>>> = asFlowApiResult {
        nexonService.requestTradeInfo(authorization = apiKey, accessid = accessId, tradeType = tradeType, offset = offset, limit = limit).asTradeInfo()
    }

    suspend fun getPlayerImg(
        spId: Int
    ) : Flow<CommonApiResult<ByteArray>> = asFlowApiResult {
        nexonService.requestPlayerImage(authorization = apiKey, spid = spId).asImageByteArray()
    }

    suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ) : Flow<CommonApiResult<List<RankerPlayer>>> = asFlowApiResult {
        nexonService.requestRankerPlayerAverList(authorization = apiKey, matchType = matchType, players = players).asRankerPlayers()
    }

    suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ) : Flow<CommonApiResult<ResponseBody>> = asFlowApiResult {
        nexonService.requestPlayerInfo(spId = spId, strong = n1Strong)
    }

    suspend fun getRank(
        page : Int
    ) : Flow<CommonApiResult<ResponseBody>> = asFlowApiResult {
        nexonService.requestRank(page = page)
    }

    suspend fun getPlayerName() : Flow<CommonApiResult<ResponseBody>> =
        asFlowApiResult { nexonService.requestPlayerName(authorization = apiKey) }

    suspend fun getSeasonIds() : Flow<CommonApiResult<ResponseBody>> =
        asFlowApiResult { nexonService.requestSeasonIdList(authorization = apiKey) }
}