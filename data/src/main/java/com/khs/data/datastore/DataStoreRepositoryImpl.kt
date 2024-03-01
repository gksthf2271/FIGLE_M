package com.khs.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import com.khs.domain.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    private object PreferenceKeys {
        val KEY_SEASON_UPDATE_TIME = longPreferencesKey("season_update_timeMs")
        val KEY_PLAYER_UPDATE_TIME = longPreferencesKey("player_update_timeMs")
    }

    override suspend fun editSeasonUpdateTime(timeMs: Long) {
        dataStore.edit {
            it[PreferenceKeys.KEY_SEASON_UPDATE_TIME] = timeMs
        }
    }

    override suspend fun getSeasonUpdateTime(): Flow<Long> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emptyPreferences()
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[PreferenceKeys.KEY_SEASON_UPDATE_TIME] ?: 0
    }

    override suspend fun editPlayerUpdateTime(timeMs: Long) {
        dataStore.edit {
            it[PreferenceKeys.KEY_PLAYER_UPDATE_TIME] = timeMs
        }
    }

    override suspend fun getPlayerUpdateTime(): Flow<Long> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emptyPreferences()
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[PreferenceKeys.KEY_PLAYER_UPDATE_TIME] ?: 0
    }
}