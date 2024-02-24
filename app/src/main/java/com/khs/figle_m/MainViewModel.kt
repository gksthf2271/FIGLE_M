package com.khs.figle_m

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.domain.database.usecase.LocalSetupUseCase
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.usecase.RankUseCase
import com.khs.domain.nexon.usecase.SearchUseCase
import com.khs.domain.nexon.usecase.SetupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setupUseCase: SetupUseCase,
    private val rankUseCase: RankUseCase,
    private val searchUseCase: SearchUseCase,
    private val localSetupUseCase: LocalSetupUseCase,
) : ViewModel() {

    private val _mainUIState = MutableStateFlow(MainUIState.Loading)
    val mainUIState: StateFlow<MainUIState> = _mainUIState

    fun updatePlayerAndSeasonDB() = viewModelScope.launch {
        _mainUIState.value = MainUIState.Loading
        setupUseCase.getPlayerNameList().zip(
            setupUseCase.getSeasonList()
        ) { playerResult, seasonResult ->
            Pair(playerResult, seasonResult)
        }.collectLatest { result ->
            when (result) {
                is CommonResult.Loading -> {
                    _mainUIState.value = MainUIState.Loading
                }

                is CommonResult.Success -> {
                    localSetupUseCase.updateSeasonDB(result.data)
                }

                is CommonResult.Fail -> {
//                        _mainUIState.value = MainUIState.Failed(errorCode = , errorMsg = )
                }
            }
        }
    }

    fun updateSeasonDB() = viewModelScope.launch {
        _mainUIState.value = MainUIState.Loading
        setupUseCase.getSeasonList().collectLatest { result ->
            when (result) {
                is CommonResult.Loading -> {
                    _mainUIState.value = MainUIState.Loading
                }

                is CommonResult.Success -> {
                    localSetupUseCase.updateSeasonDB(result.data)
                }

                is CommonResult.Fail -> {
//                        _mainUIState.value = MainUIState.Failed(errorCode = , errorMsg = )
                }
            }
        }
    }

    fun updatePlayerDB() = viewModelScope.launch {
        _mainUIState.value = MainUIState.Loading
        setupUseCase.getPlayerNameList().collectLatest { result ->
            when (result) {
                is CommonResult.Loading -> {
//                        _mainUIState.value = MainUIState.Loading
                }

                is CommonResult.Success -> {
                    localSetupUseCase.updatePlayerDB(result.data)
                }

                is CommonResult.Fail -> {
//                        _mainUIState.value = MainUIState.Failed(errorCode = , errorMsg = )
                }
            }
        }
    }


    sealed interface MainUIState {
        object Loading : MainUIState
        data class Success(val contents: Any?) : MainUIState
        data class Failed(val errorCode: String, val errorMsg: String) : MainUIState
    }
}