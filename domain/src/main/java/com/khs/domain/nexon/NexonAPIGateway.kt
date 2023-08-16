package com.khs.domain.nexon

import com.khs.domain.entity.CommonApiResult
import com.khs.domain.entity.HighRankUser
import com.khs.domain.entity.Match
import com.khs.domain.entity.RankerPlayer
import com.khs.domain.entity.TradeInfo
import com.khs.domain.entity.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface NexonAPIGateway {
    suspend fun searchUser(nickname: String): Flow<CommonApiResult<User>>

    suspend fun getMatchDetail(matchId: String): Flow<CommonApiResult<Match>>

    suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonApiResult<List<String>>>

    suspend fun getHighRankUser(
        accessId: String
    ) : Flow<CommonApiResult<List<HighRankUser>>>

    suspend fun getTradeInfo(
        accessId: String,
        tradeType : String,
        offset: Int?,
        limit: Int?
    ) : Flow<CommonApiResult<List<TradeInfo>>>

    suspend fun getPlayerImg(
        spId: Int
    ) : Flow<CommonApiResult<ByteArray>>

    suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ) : Flow<CommonApiResult<List<RankerPlayer>>>

    suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ) : Flow<CommonApiResult<ResponseBody>>

    suspend fun getRank(
        page : Int
    ) : Flow<CommonApiResult<ResponseBody>>

    suspend fun getPlayerName() : Flow<CommonApiResult<ResponseBody>>

    suspend fun getSeasonIds() : Flow<CommonApiResult<ResponseBody>>
}