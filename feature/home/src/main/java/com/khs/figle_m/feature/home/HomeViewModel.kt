package com.khs.figle_m.feature.home

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
class HomeViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel() {
    private val CLASS_TAG = "HomeViewModel"
    private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState.Idle)
    val uiState: StateFlow<HomeUIState> = _uiState

    fun searchUser(nickName: String, teamPrice: String = "") = viewModelScope.launch {
        LogUtil.dLog(LogUtil.TAG_SEARCH, CLASS_TAG,"searchUser > $nickName, teamPrice: $teamPrice")
        _uiState.value = HomeUIState.Loading

        searchUseCase
            .search(nickName)
            .collectLatest { result ->
                when (result) {
                    is CommonResult.Success -> {
                        LogUtil.dLog(LogUtil.TAG_SEARCH, CLASS_TAG, "검색 결과 : ${result.data.accessId} / ${result.data.nickname}")
                        // Apply teamPrice to the user data
                        val userWithTeamPrice = result.data.apply {
                            this.teamPrice = teamPrice
                        }
                        _uiState.value = HomeUIState.Success(userWithTeamPrice)
                    }
                    is CommonResult.Fail.Error -> {
                        _uiState.value = HomeUIState.Failed(
                            errorCode = result.resultCode,
                            errorMsg = result.errorMsg
                        )
                    }

                    is CommonResult.Fail.Exception -> {
                        _uiState.value = HomeUIState.Failed(
                            errorCode = -1,
                            errorMsg = result.exception.message
                        )
                    }
                    else -> {
                        _uiState.value = HomeUIState.Loading
                    }
                }
            }
    }

    fun resetState() {
        _uiState.value = HomeUIState.Idle
    }
}

sealed interface HomeUIState {
    object Idle : HomeUIState
    object Loading : HomeUIState
    data class Success(val userResponse: com.khs.data.nexon_api.response.UserResponse) : HomeUIState
    data class Failed(val errorCode: Int, val errorMsg: String? = "") : HomeUIState
}
