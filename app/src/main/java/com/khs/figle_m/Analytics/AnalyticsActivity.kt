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
        setContentView(R.layout.activity_analytics)
    }

    override fun onStart() {
        super.onStart()
        val myList = intent.getParcelableArrayListExtra<MatchInfoDTO>(KEY_MY_DATA)
        val opposingUserList =
            intent.getParcelableArrayListExtra<MatchInfoDTO>(KEY_OPPOSING_USER_DATA)
        var analyticsFragment = AnalyticsFragment()
        var bundle = Bundle()
        bundle.putParcelableArrayList(KEY_MY_DATA, myList)
        bundle.putParcelableArrayList(KEY_OPPOSING_USER_DATA, opposingUserList)
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