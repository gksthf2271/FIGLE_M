package com.khs.figle_m.feature.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.domain.nexon.NexonAPIGateway
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.Player
import com.khs.figle_m.common.model.AnalyticsPlayer
import com.khs.figle_m.common.model.ParentPositionEnum
import com.khs.figle_m.common.util.CrawlingUtils
import com.khs.figle_m.common.util.LogUtil
import com.khs.figle_m.common.util.PositionEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.PriorityQueue
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val nexonAPIGateway: NexonAPIGateway
) : ViewModel() {
    private val CLASS_TAG = "AnalyticsViewModel"

    private val _uiState: MutableStateFlow<AnalyticsUIState> = MutableStateFlow(AnalyticsUIState.Loading)
    val uiState: StateFlow<AnalyticsUIState> = _uiState

    fun loadAnalytics(accessId: String, matchIdList: List<String>) = viewModelScope.launch {
        LogUtil.dLog(LogUtil.TAG_UI, CLASS_TAG, "loadAnalytics > accessId: $accessId, matchIds: ${matchIdList.size}")
        _uiState.value = AnalyticsUIState.Loading

        // Load match details
        val resultList = mutableListOf<Match>()
        val failedQueue = PriorityQueue<String>()

        for (matchId in matchIdList) {
            nexonAPIGateway.getMatchDetail(matchId).collect { result ->
                when (result) {
                    is CommonResult.Success -> {
                        resultList.add(result.data)
                        if (resultList.size + failedQueue.size == matchIdList.size) {
                            processMatchDetails(accessId, resultList)
                        }
                    }
                    is CommonResult.Fail -> {
                        failedQueue.add(matchId)
                        if (resultList.size + failedQueue.size == matchIdList.size) {
                            processMatchDetails(accessId, resultList)
                        }
                    }
                    is CommonResult.Loading -> {
                        // Ignore
                    }
                }
            }
        }
    }

    private fun processMatchDetails(accessId: String, matchDetailList: List<Match>) = viewModelScope.launch {
        val playerMap = hashMapOf<Int, ArrayList<Player>>()

        for (match in matchDetailList) {
            if (match.matchInfo.size != 2) continue

            val matchInfo = if (accessId == match.matchInfo[0].accessId) {
                match.matchInfo[0]
            } else {
                match.matchInfo[1]
            }

            for (player in matchInfo.player) {
                if (player.spPosition == PositionEnum.SUB.spposition) continue
                val playerList = playerMap.getOrPut(player.spId) { arrayListOf() }
                playerList.add(player)
            }
        }

        val playerInfoList = playerMap.map { (spId, players) ->
            val playerInfo = AnalyticsPlayer(spId = spId)

            for (player in players) {
                with(playerInfo.totalData) {
                    totalShoot += player.status.shoot
                    totalEffectiveShoot += player.status.effectiveShoot
                    totalAssist += player.status.assist
                    totalGoal += player.status.goal
                    totalDribble += player.status.dribble
                    totalPassTry += player.status.passTry
                    totalPassSuccess += player.status.passSuccess
                    totalBlock += player.status.block
                    totalTackle += player.status.tackle
                    totalSpRating += player.status.spRating
                }

                playerInfo.position = when (player.spPosition) {
                    in 1..8 -> ParentPositionEnum.D
                    in 9..19 -> ParentPositionEnum.M
                    in 20..27 -> ParentPositionEnum.F
                    0 -> ParentPositionEnum.GK
                    else -> ParentPositionEnum.NONE
                }
            }

            playerInfo.playerDataList = players
            playerInfo
        }.toMutableList()

        // Load player images
        loadPlayerImages(playerInfoList)
    }

    private fun loadPlayerImages(playerInfoList: List<AnalyticsPlayer>) = viewModelScope.launch {
        val responseQueue = PriorityQueue<String>()

        for (item in playerInfoList) {
            CrawlingUtils.getPlayerImg(item.spId, 1, {
                item.imageResUrl = it
                responseQueue.add(it)
                if (responseQueue.size == playerInfoList.size) {
                    _uiState.value = AnalyticsUIState.Success(
                        ratingTopPlayers = playerInfoList
                            .sortedByDescending { it.totalData.totalSpRating / it.playerDataList.size }
                            .take(10),
                        goalTopPlayers = playerInfoList
                            .sortedByDescending { it.totalData.totalGoal }
                            .take(5),
                        assistTopPlayers = playerInfoList
                            .sortedByDescending { it.totalData.totalAssist }
                            .take(5)
                    )
                }
            }, {
                responseQueue.add("")
                if (responseQueue.size == playerInfoList.size) {
                    _uiState.value = AnalyticsUIState.Success(
                        ratingTopPlayers = playerInfoList
                            .sortedByDescending { it.totalData.totalSpRating / it.playerDataList.size }
                            .take(10),
                        goalTopPlayers = playerInfoList
                            .sortedByDescending { it.totalData.totalGoal }
                            .take(5),
                        assistTopPlayers = playerInfoList
                            .sortedByDescending { it.totalData.totalAssist }
                            .take(5)
                    )
                }
            })
        }
    }
}

sealed interface AnalyticsUIState {
    object Loading : AnalyticsUIState
    data class Success(
        val ratingTopPlayers: List<AnalyticsPlayer>,
        val goalTopPlayers: List<AnalyticsPlayer>,
        val assistTopPlayers: List<AnalyticsPlayer>
    ) : AnalyticsUIState
    data class Failed(val errorCode: Int) : AnalyticsUIState
}
