package com.khs.figle_m.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.figle_m.common.model.Ranker
import com.khs.figle_m.common.util.CrawlingUtils
import com.khs.figle_m.common.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor() : ViewModel() {
    private val CLASS_TAG = "RankingViewModel"

    private val _uiState: MutableStateFlow<RankingUIState> = MutableStateFlow(RankingUIState.Loading)
    val uiState: StateFlow<RankingUIState> = _uiState

    private val _selectedRanker: MutableStateFlow<Ranker?> = MutableStateFlow(null)
    val selectedRanker: StateFlow<Ranker?> = _selectedRanker

    init {
        loadRankingList()
    }

    fun loadRankingList(page: Int = 1) = viewModelScope.launch {
        LogUtil.dLog(LogUtil.TAG_RANK, CLASS_TAG, "loadRankingList > page: $page")
        _uiState.value = RankingUIState.Loading

        CrawlingUtils.getRanking(
            page,
            onSuccess = { rankerList ->
                LogUtil.dLog(LogUtil.TAG_RANK, CLASS_TAG, "getRankingList Success! ${rankerList.size} items")
                if (rankerList.isNotEmpty()) {
                    _selectedRanker.value = rankerList[0]
                }
                _uiState.value = RankingUIState.Success(rankerList)
            },
            onFailed = { errorCode ->
                LogUtil.vLog(LogUtil.TAG_RANK, CLASS_TAG, "getRankingList Failed! $errorCode")
                _uiState.value = RankingUIState.Failed(errorCode)
            }
        )
    }

    fun selectRanker(ranker: Ranker) {
        _selectedRanker.value = ranker
    }
}

sealed interface RankingUIState {
    object Loading : RankingUIState
    data class Success(val rankerList: List<Ranker>) : RankingUIState
    data class Failed(val errorCode: Int) : RankingUIState
}
