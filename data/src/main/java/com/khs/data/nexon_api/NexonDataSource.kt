package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.asHighRanker
import com.khs.data.nexon_api.response.asImageByteArray
import com.khs.data.nexon_api.response.asMatch
import com.khs.data.nexon_api.response.asMatchIds
import com.khs.data.nexon_api.response.asPlayerList
import com.khs.data.nexon_api.response.asRankerPlayers
import com.khs.data.nexon_api.response.asSeasonList
import com.khs.data.nexon_api.response.asTradeInfo
import com.khs.data.nexon_api.response.asUser
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.HighRankUser
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.RankerPlayer
import com.khs.domain.nexon.entity.TradeInfo
import com.khs.domain.nexon.entity.User
import com.khs.domain.util.Utils.asFlowResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class NexonDataSource@Inject constructor(
    private val apiKey: String,
    private val nexonAPIService: NexonAPIService,
    private val nexonCDNService: NexonCDNService,
    private val nexonStaticService: NexonStaticService,
    private val nexonDataCenterService: NexonDataCenterService) {

    suspend fun searchUser(nickname: String): Flow<CommonResult<User>> =
        asFlowResult { nexonAPIService.requestUser(authorization = apiKey, nickname = nickname).asUser() }

    suspend fun getMatchDetail(matchId: String): Flow<CommonResult<Match>> =
        asFlowResult { nexonAPIService.requestMatchDetail(authorization = apiKey, matchid = matchId).asMatch() }

    suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonResult<List<String>>> = asFlowResult {
        nexonAPIService.requestMatchIds(authorization = apiKey, accessid = accessId, matchtype = matchType, offset = offset, limit = limit).asMatchIds()
    }

    suspend fun getHighRankUser(
        accessId: String
    ) : Flow<CommonResult<List<HighRankUser>>> = asFlowResult {
        nexonAPIService.requestHighRanker(authorization = apiKey, accessid = accessId).asHighRanker()
    }

    suspend fun getTradeInfo(
        accessId: String,
        tradeType : String,
        offset: Int?,
        limit: Int?
    ) : Flow<CommonResult<List<TradeInfo>>> = asFlowResult {
        nexonAPIService.requestTradeInfo(authorization = apiKey, accessid = accessId, tradeType = tradeType, offset = offset, limit = limit).asTradeInfo()
    }

    suspend fun getPlayerImg(
        spId: Int
    ) : Flow<CommonResult<ByteArray>> = asFlowResult {
        nexonCDNService.requestPlayerImage(authorization = apiKey, spid = spId).asImageByteArray()
    }

    suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ) : Flow<CommonResult<List<RankerPlayer>>> = asFlowResult {
        nexonAPIService.requestRankerPlayerAverList(authorization = apiKey, matchType = matchType, players = players).asRankerPlayers()
    }

    suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ) : Flow<CommonResult<ResponseBody>> = asFlowResult {
        nexonDataCenterService.requestPlayerInfo(spId = spId, strong = n1Strong)
    }

    suspend fun getRank(
        page : Int
    ) : Flow<CommonResult<ResponseBody>> = asFlowResult {
        nexonDataCenterService.requestRank(page = page)
    }

    suspend fun getPlayerName() : Flow<CommonResult<List<Player>>> =
        asFlowResult { nexonStaticService.requestPlayerName(authorization = apiKey).asPlayerList() }

    suspend fun getSeasonIds() : Flow<CommonResult<List<Season>>> =
        asFlowResult { nexonStaticService.requestSeasonIdList(authorization = apiKey).asSeasonList() }
}