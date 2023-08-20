package com.khs.domain.nexon

import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.HighRankUser
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.RankerPlayer
import com.khs.domain.nexon.entity.TradeInfo
import com.khs.domain.nexon.entity.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface NexonAPIGateway {
    suspend fun searchUser(nickname: String): Flow<CommonResult<User>>

    suspend fun getMatchDetail(matchId: String): Flow<CommonResult<Match>>

    suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonResult<List<String>>>

    suspend fun getHighRankUser(
        accessId: String
    ) : Flow<CommonResult<List<HighRankUser>>>

    suspend fun getTradeInfo(
        accessId: String,
        tradeType : String,
        offset: Int?,
        limit: Int?
    ) : Flow<CommonResult<List<TradeInfo>>>

    suspend fun getPlayerImg(
        spId: Int
    ) : Flow<CommonResult<ByteArray>>

    suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ) : Flow<CommonResult<List<RankerPlayer>>>

    suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ) : Flow<CommonResult<ResponseBody>>

    suspend fun getRank(
        page : Int
    ) : Flow<CommonResult<ResponseBody>>

    suspend fun getPlayerNameList() : Flow<CommonResult<List<Player>>>

    suspend fun getSeasonList() : Flow<CommonResult<List<Season>>>
}