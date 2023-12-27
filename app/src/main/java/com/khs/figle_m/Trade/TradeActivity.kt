package com.khs.figle_m.Trade

import android.os.Bundle
import android.util.Log
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.R
import com.khs.figle_m.Utils.FragmentUtils
import com.khs.figle_m.Utils.LogUtil

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
        val ouid = intent.getStringExtra(KEY_ACCESS_ID)
        val tradeFragment = TradeHomeFragment()
        val bundle = Bundle()
        bundle.putString(KEY_ACCESS_ID, ouid)
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