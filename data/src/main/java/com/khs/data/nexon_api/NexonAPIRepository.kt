package com.khs.data.nexon_api

import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.NexonAPIGateway
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.HighRankUser
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.RankerPlayer
import com.khs.domain.nexon.entity.TradeInfo
import com.khs.domain.nexon.entity.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class NexonAPIRepository @Inject constructor(
    private val nexonDataSource: NexonDataSource
) : NexonAPIGateway {
    override suspend fun searchUser(nickname: String): Flow<CommonResult<User>> =
        nexonDataSource.searchUser(nickname = nickname)

    override suspend fun getMatchDetail(matchId: String): Flow<CommonResult<Match>> =
        nexonDataSource.getMatchDetail(matchId = matchId)

    override suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonResult<List<String>>> = nexonDataSource.getMatchIds(
        accessId = accessId,
        matchType = matchType,
        offset = offset,
        limit = limit
    )

    override suspend fun getHighRankUser(accessId: String): Flow<CommonResult<List<HighRankUser>>> =
        nexonDataSource.getHighRankUser(accessId = accessId)

    override suspend fun getTradeInfo(
        accessId: String,
        tradeType: String,
        offset: Int?,
        limit: Int?
    ): Flow<CommonResult<List<TradeInfo>>> =
        nexonDataSource.getTradeInfo(
            accessId = accessId,
            tradeType = tradeType,
            offset = offset,
            limit = limit
        )

    override suspend fun getPlayerImg(spId: Int): Flow<CommonResult<ByteArray>> =
        nexonDataSource.getPlayerImg(spId = spId)

    override suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ): Flow<CommonResult<List<RankerPlayer>>> =
        nexonDataSource.getRankerPlayerAverList(matchType = matchType, players = players)

    override suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ): Flow<CommonResult<ResponseBody>> =
        nexonDataSource.getPlayerInfo(spId = spId, n1Strong = n1Strong)

    override suspend fun getRank(page: Int): Flow<CommonResult<ResponseBody>> =
        nexonDataSource.getRank(page = page)

    override suspend fun getPlayerNameList(): Flow<CommonResult<List<Player>>> =
        nexonDataSource.getPlayerName()

    override suspend fun getSeasonList(): Flow<CommonResult<List<Season>>> =
        nexonDataSource.getSeasonIds()
}