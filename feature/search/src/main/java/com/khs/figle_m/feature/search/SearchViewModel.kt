package com.khs.figle_m.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.usecase.SearchUseCase
import com.khs.figle_m.common.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel() {
    private val CLASS_TAG = "SearchViewModel"
    private val _uiState: MutableStateFlow<SearchUIState> = MutableStateFlow(SearchUIState.Loading)
    val uiState: StateFlow<SearchUIState> = _uiState

    fun search(nickName: String) = viewModelScope.launch {
        LogUtil.dLog(LogUtil.TAG_SEARCH, CLASS_TAG,"search > $nickName")
        searchUseCase
            .search(nickName)
            .collectLatest { result ->
                when (result) {
                    is CommonResult.Success -> {
                        LogUtil.dLog(LogUtil.TAG_SEARCH, CLASS_TAG, "검색 결과 : ${result.data.accessId} / ${result.data.nickname}")
                        _uiState.value = SearchUIState.Success(null)
                    }
                    is CommonResult.Fail.Error -> {
                        _uiState.value = SearchUIState.Failed(
                            errorCode = result.resultCode,
                            errorMsg = result.errorMsg
                        )
                    }

                    is CommonResult.Fail.Exception -> {
                        _uiState.value = SearchUIState.Failed(
                            errorCode = -1,
                            errorMsg = result.exception.message
                        )
                    }
                    else -> {
                        _uiState.value = SearchUIState.Loading
                    }
                }
            }
    }
}


sealed interface SearchUIState {
    object Loading : SearchUIState
    data class Success(val contents: Any?) : SearchUIState
    data class Failed(val errorCode: Int, val errorMsg: String? = "") : SearchUIState
}