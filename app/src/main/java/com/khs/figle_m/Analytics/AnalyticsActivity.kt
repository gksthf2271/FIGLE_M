package com.khs.figle_m.Analytics

import android.os.Bundle
import android.util.Log
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.utils.FragmentUtils

class AnalyticsActivity : BaseActivity() {
    val TAG: String = javaClass.name
    val KEY_MY_DATA = "KEY_MY_DATA"
    val KEY_ACCESS_ID = "KEY_ACCESS_ID"
    val KEY_OPPOSING_USER_DATA = "KEY_OPPOSING_USER_DATA"
    companion object {
        @Volatile
        private var instance: AnalyticsActivity? = null

        @JvmStatic
        fun getInstance(): AnalyticsActivity =
            instance ?: synchronized(this) {
                instance
                    ?: AnalyticsActivity().also {
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
        val myList = intent.getStringArrayListExtra(KEY_MY_DATA)
        val accessId = intent.getStringExtra(KEY_ACCESS_ID)
        var analyticsFragment = AnalyticsFragment()
        var bundle = Bundle()
        bundle.putStringArrayList(KEY_MY_DATA, myList)
        bundle.putString(KEY_ACCESS_ID, accessId)
        analyticsFragment.arguments = bundle
        FragmentUtils().loadFragment(
            analyticsFragment,
            R.id.fragment_container,
            supportFragmentManager
        )
    }

    override fun initPresenter() {
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"onDestory(...)")

    }
}