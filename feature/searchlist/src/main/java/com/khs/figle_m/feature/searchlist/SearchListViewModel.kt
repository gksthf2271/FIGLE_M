package com.khs.figle_m.feature.searchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.domain.nexon.NexonAPIGateway
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.entity.Match
import com.khs.figle_m.common.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private val nexonAPIGateway: NexonAPIGateway
) : ViewModel() {
    private val CLASS_TAG = "SearchListViewModel"

    private val _uiState: MutableStateFlow<SearchListUIState> = MutableStateFlow(SearchListUIState.Loading)
    val uiState: StateFlow<SearchListUIState> = _uiState

    private val _matchList = MutableStateFlow<List<Match>>(emptyList())

    fun loadMatchList(matchIdList: List<String>) = viewModelScope.launch {
        LogUtil.dLog(LogUtil.TAG_SEARCH, CLASS_TAG, "loadMatchList > matchIds: ${matchIdList.size}")
        _uiState.value = SearchListUIState.Loading

        val resultList = mutableListOf<Match>()
        var loadedCount = 0

        for (matchId in matchIdList) {
            nexonAPIGateway.getMatchDetail(matchId).collect { result ->
                when (result) {
                    is CommonResult.Success -> {
                        resultList.add(result.data)
                        loadedCount++

                        if (loadedCount == matchIdList.size) {
                            _matchList.value = resultList
                            _uiState.value = SearchListUIState.Success(resultList)
                        }
                    }
                    is CommonResult.Fail.Error -> {
                        LogUtil.vLog(LogUtil.TAG_SEARCH, CLASS_TAG, "loadMatchDetail failed: ${result.resultCode} - ${result.errorMsg}")
                        loadedCount++

                        if (loadedCount == matchIdList.size) {
                            _matchList.value = resultList
                            _uiState.value = SearchListUIState.Success(resultList)
                        }
                    }
                    is CommonResult.Fail.Exception -> {
                        LogUtil.vLog(LogUtil.TAG_SEARCH, CLASS_TAG, "loadMatchDetail exception: ${result.exception.message}")
                        loadedCount++

                        if (loadedCount == matchIdList.size) {
                            _matchList.value = resultList
                            _uiState.value = SearchListUIState.Success(resultList)
                        }
                    }
                    is CommonResult.Loading -> {
                        // Already in loading state
                    }
                }
            }
        }
    }
}

sealed interface SearchListUIState {
    object Loading : SearchListUIState
    data class Success(val matchList: List<Match>) : SearchListUIState
    data class Failed(val errorCode: Int) : SearchListUIState
}
