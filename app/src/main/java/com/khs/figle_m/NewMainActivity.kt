package com.khs.figle_m

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.khs.figle_m.databinding.ActivityMainBinding
import com.khs.figle_m.home.HomeFragment
import com.khs.figle_m.utils.FragmentUtils
import com.khs.figle_m.utils.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

@AndroidEntryPoint
class NewMainActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName
    lateinit var mBinding: ActivityMainBinding
    private val mMainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        mMainViewModel.checkPlayerAndSeasonDB()
        lifecycleScope.launch {
            mMainViewModel.mainUIState.collectLatest {
                if (it is MainViewModel.MainUIState.Success) {
                    showMainActivity()
                }

            }
        }
    }

    fun showMainActivity() {
        CoroutineScope(Dispatchers.Main).launch {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "showMainActivity(...)")
            val fm: FragmentManager = this@NewMainActivity.supportFragmentManager
            val homeFragment: HomeFragment = HomeFragment.getInstance()
            FragmentUtils().loadFragment(homeFragment, R.id.fragment_container, fm)
        }
    }
}