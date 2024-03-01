package com.khs.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun editSeasonUpdateTime(timeMs:Long)
    suspend fun getSeasonUpdateTime(): Flow<Long>

    suspend fun editPlayerUpdateTime(timeMs:Long)
    suspend fun getPlayerUpdateTime(): Flow<Long>
}