package com.khs.figle_m.feature.searchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.common.data.DataManager
import com.khs.figle_m.common.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor() : ViewModel() {
    private val CLASS_TAG = "SearchListViewModel"

    private val _uiState: MutableStateFlow<SearchListUIState> = MutableStateFlow(SearchListUIState.Loading)
    val uiState: StateFlow<SearchListUIState> = _uiState

    private val _matchList = MutableStateFlow<List<MatchDetailResponse>>(emptyList())

    fun loadMatchList(matchIdList: List<String>) = viewModelScope.launch {
        LogUtil.dLog(LogUtil.TAG_SEARCH, CLASS_TAG, "loadMatchList > matchIds: ${matchIdList.size}")
        _uiState.value = SearchListUIState.Loading

        val resultList = mutableListOf<MatchDetailResponse>()
        var loadedCount = 0

        for (matchId in matchIdList) {
            DataManager.loadMatchDetail(matchId, { matchDetail ->
                resultList.add(matchDetail)
                loadedCount++

                if (loadedCount == matchIdList.size) {
                    _matchList.value = resultList
                    _uiState.value = SearchListUIState.Success(resultList)
                }
            }, { error ->
                LogUtil.vLog(LogUtil.TAG_SEARCH, CLASS_TAG, "loadMatchDetail failed: $error")
                loadedCount++

                if (loadedCount == matchIdList.size) {
                    _matchList.value = resultList
                    _uiState.value = SearchListUIState.Success(resultList)
                }
            })
        }
    }
}

sealed interface SearchListUIState {
    object Loading : SearchListUIState
    data class Success(val matchList: List<MatchDetailResponse>) : SearchListUIState
    data class Failed(val errorCode: Int) : SearchListUIState
}
