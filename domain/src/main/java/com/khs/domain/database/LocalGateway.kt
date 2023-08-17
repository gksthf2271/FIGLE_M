package com.khs.domain.database

import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.CommonResult
import kotlinx.coroutines.flow.Flow

interface LocalGateway {
    suspend fun getAllSeason() : Flow<CommonResult<List<Season>>>
    suspend fun getSeason(seasonId: String) : Flow<CommonResult<Season>>
    suspend fun deleteAllSeason()
    suspend fun updateSeasonDB(seasonList: List<String>)

    suspend fun getAllPlayer() : Flow<CommonResult<List<Player>>>
    suspend fun getPlayer(playerId: String) : Flow<CommonResult<Player?>>
    suspend fun deleteAllPlayer()
    suspend fun updatePlayerDB(playerList : List<String>)
}