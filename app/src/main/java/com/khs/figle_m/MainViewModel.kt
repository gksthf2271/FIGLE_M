package com.khs.figle_m

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.domain.database.usecase.LocalSetupUseCase
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.usecase.RankUseCase
import com.khs.domain.nexon.usecase.SearchUseCase
import com.khs.domain.nexon.usecase.SetupUseCase
import com.khs.figle_m.utils.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _mainUIState: MutableStateFlow<MainUIState> = MutableStateFlow(MainUIState.Loading)
    val mainUIState: StateFlow<MainUIState> = _mainUIState

    private var playerDBUpdateTime: Long = 0
    private var seasonDBUpdateTime: Long = 0

    fun checkPlayerAndSeasonDB() = viewModelScope.launch {
        localSetupUseCase.getPlayerDBUpdateTime()
            .zip(localSetupUseCase.getSeasonDBUpdateTime()) { playerUpdateTime, seasonUpdateTime ->
                LogUtil.dLog(
                    LogUtil.TAG_SETUP,
                    "Main",
                    "playerUpdateTime : $playerUpdateTime / seasonUpdateTime : $seasonUpdateTime"
                )
                playerDBUpdateTime = playerUpdateTime
                seasonDBUpdateTime = seasonUpdateTime
            }.collectLatest {
                updateSeasonDB()
                updatePlayerDB()
            }
    }

    private fun updateSeasonDB() = viewModelScope.launch {
        setupUseCase.getSeasonList().collectLatest { result ->
            when (result) {
                is CommonResult.Success -> {
                    LogUtil.dLog(LogUtil.TAG_SETUP, "Main", "시즌 > 저장된 시간 : $seasonDBUpdateTime / API 데이터 업데이트 시간 : ${result.data.lastModified}")
                    if (seasonDBUpdateTime < result.data.lastModified) {
                        localSetupUseCase.updateSeasonDB(result.data.seeasonList)
                        localSetupUseCase.editSeasonUpdateTime(result.data.lastModified)
                    }
                    _mainUIState.value = MainUIState.Success(null)
                }
                is CommonResult.Fail.Error -> {
                    _mainUIState.value = MainUIState.Failed(errorCode = result.resultCode, errorMsg = result.errorMsg)
                }

                is CommonResult.Fail.Exception -> {
                    _mainUIState.value = MainUIState.Failed(errorCode = -1, errorMsg = result.exception.message)
                }
                else -> {
                    _mainUIState.value = MainUIState.Loading
                }
            }
        }
    }

    private fun updatePlayerDB() = viewModelScope.launch {
        setupUseCase.getPlayerNameList().collectLatest { result ->
            when (result) {
                is CommonResult.Success -> {
                    LogUtil.dLog(LogUtil.TAG_SETUP, "Main", "플레이어 > 저장된 시간 : $playerDBUpdateTime / API 데이터 업데이트 시간 : ${result.data.lastModified}")
                    if (playerDBUpdateTime < result.data.lastModified) {
                        localSetupUseCase.updatePlayerDB(result.data.playerList)
                        localSetupUseCase.editPlayerUpdateTime(result.data.lastModified)
                        _mainUIState.value = MainUIState.Success(null)
                    }
                }
                is CommonResult.Fail.Error -> {
                    _mainUIState.value = MainUIState.Failed(errorCode = result.resultCode, errorMsg = result.errorMsg)
                }

                is CommonResult.Fail.Exception -> {
                    _mainUIState.value = MainUIState.Failed(errorCode = -1, errorMsg = result.exception.message)
                }

                else -> {
                    _mainUIState.value = MainUIState.Loading
                }
            }
        }
    }

    sealed interface MainUIState {
        object Loading : MainUIState
        data class Success(val contents: Any?) : MainUIState
        data class Failed(val errorCode: Int, val errorMsg: String? = "") : MainUIState
    }
}