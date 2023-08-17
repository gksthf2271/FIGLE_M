package com.khs.figle_m

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.domain.database.usecase.LocalSetupUseCase
import com.khs.domain.nexon.entity.CommonResult
import com.khs.domain.nexon.usecase.SetupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setupUseCase: SetupUseCase,
    private val localSetupUseCase: LocalSetupUseCase,
) : ViewModel() {

    fun updateSeasonDB() {
        viewModelScope.launch {
            setupUseCase.getSeasonIds().collectLatest { result ->
                when (result) {
                    is CommonResult.Loading -> {

                    }

                    is CommonResult.Success -> {
                        localSetupUseCase.updateSeasonDB(result.data)
                    }

                    is CommonResult.Fail -> {

                    }
                }
            }
        }
    }

    fun updatePlayerDB() {
        viewModelScope.launch {
            setupUseCase.getPlayerNames().collectLatest { result ->
                when (result) {
                    is CommonResult.Loading -> {

                    }

                    is CommonResult.Success -> {
                        localSetupUseCase.updatePlayerDB(result.data)
                    }

                    is CommonResult.Fail -> {
                        if (result is CommonResult.Fail.Error) {

                        } else {

                        }
                    }
                }
            }
        }
    }
}