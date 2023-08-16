package com.khs.figle_m.trade

import android.os.Bundle
import com.khs.figle_m.base.BaseActivity
import com.khs.figle_m.R
import com.khs.figle_m.utils.FragmentUtils
import com.khs.figle_m.utils.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TradeActivity : BaseActivity() {
    val TAG: String = javaClass.simpleName
    val KEY_ACCESS_ID = "KEY_ACCESS_ID"
    companion object {
        @Volatile
        private var instance: TradeActivity? = null

        @JvmStatic
        fun getInstance(): TradeActivity =
            instance ?: synchronized(this) {
                instance
                    ?: TradeActivity().also {
                        instance = it
                    }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun onStart() {
        super.onStart()
        val accessId = intent.getStringExtra(KEY_ACCESS_ID)
        val tradeFragment = TradeHomeFragment()
        val bundle = Bundle()
        bundle.putString(KEY_ACCESS_ID, accessId)
        tradeFragment.arguments = bundle
        FragmentUtils().loadFragment(
            tradeFragment,
            R.id.fragment_container,
            supportFragmentManager
        )
    }

    override fun initPresenter() {
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onDestroy(...)")

    }
}