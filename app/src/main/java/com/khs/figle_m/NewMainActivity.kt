package com.khs.figle_m

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khs.figle_m.core.NetworkMonitor
import com.khs.figle_m.ui.FigleApp
import com.khs.figle_m.ui.theme.FigleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewMainActivity : ComponentActivity() {
    private val TAG: String = javaClass.simpleName

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val mMainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mMainViewModel.checkPlayerAndSeasonDB()
                mMainViewModel.mainUIState
                    .collectLatest {
                        splashScreen.setKeepOnScreenCondition {
                            when (it) {
                                MainViewModel.MainUIState.Loading -> true
                                is MainViewModel.MainUIState.Success -> false
                                else -> {
                                    false
                                }
                            }
                        }
                    }
            }
        }
        enableEdgeToEdge()

        setContent {
            FigleTheme() {
                FigleApp(
                    networkMonitor = networkMonitor
                )
            }
        }
    }
}

private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)