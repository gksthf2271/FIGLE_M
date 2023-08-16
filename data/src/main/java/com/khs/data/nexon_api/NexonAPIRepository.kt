package com.khs.data.nexon_api

import com.khs.domain.entity.CommonApiResult
import com.khs.domain.entity.HighRankUser
import com.khs.domain.entity.Match
import com.khs.domain.entity.RankerPlayer
import com.khs.domain.entity.TradeInfo
import com.khs.domain.entity.User
import com.khs.domain.nexon.NexonAPIGateway
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class NexonAPIRepository @Inject constructor(
    private val nexonDataSource: NexonDataSource
) : NexonAPIGateway {
    override suspend fun searchUser(nickname: String): Flow<CommonApiResult<User>> =
        nexonDataSource.searchUser(nickname = nickname)

    override suspend fun getMatchDetail(matchId: String): Flow<CommonApiResult<Match>> =
        nexonDataSource.getMatchDetail(matchId = matchId)

    override suspend fun getMatchIds(
        accessId: String,
        matchType: Int,
        offset: Int?,
        limit: Int?
    ): Flow<CommonApiResult<List<String>>> = nexonDataSource.getMatchIds(
        accessId = accessId,
        matchType = matchType,
        offset = offset,
        limit = limit
    )

    override suspend fun getHighRankUser(accessId: String): Flow<CommonApiResult<List<HighRankUser>>> =
        nexonDataSource.getHighRankUser(accessId = accessId)

    override suspend fun getTradeInfo(
        accessId: String,
        tradeType: String,
        offset: Int?,
        limit: Int?
    ): Flow<CommonApiResult<List<TradeInfo>>> =
        nexonDataSource.getTradeInfo(
            accessId = accessId,
            tradeType = tradeType,
            offset = offset,
            limit = limit
        )

    override suspend fun getPlayerImg(spId: Int): Flow<CommonApiResult<ByteArray>> =
        nexonDataSource.getPlayerImg(spId = spId)

    override suspend fun getRankerPlayerAverList(
        matchType: Int,
        players: String
    ): Flow<CommonApiResult<List<RankerPlayer>>> =
        nexonDataSource.getRankerPlayerAverList(matchType = matchType, players = players)

    override suspend fun getPlayerInfo(
        spId: Int,
        n1Strong: Int
    ): Flow<CommonApiResult<ResponseBody>> =
        nexonDataSource.getPlayerInfo(spId = spId, n1Strong = n1Strong)

    override suspend fun getRank(page: Int): Flow<CommonApiResult<ResponseBody>> =
        nexonDataSource.getRank(page = page)

    override suspend fun getPlayerName(): Flow<CommonApiResult<ResponseBody>> =
        nexonDataSource.getPlayerName()

    override suspend fun getSeasonIds(): Flow<CommonApiResult<ResponseBody>> =
        nexonDataSource.getSeasonIds()
}