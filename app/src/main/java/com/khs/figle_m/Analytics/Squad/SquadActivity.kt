package com.khs.figle_m.Analytics

import android.os.Bundle
import com.khs.figle_m.Analytics.Squad.SquadFragment
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.R
import com.khs.figle_m.Utils.FragmentUtils
import com.khs.figle_m.Utils.LogUtil

class SquadActivity : BaseActivity() {
    val TAG: String = javaClass.simpleName
    val KEY_MY_DATA = "KEY_MY_DATA"
    val KEY_ACCESS_ID = "KEY_ACCESS_ID"
    val KEY_OPPOSING_USER_DATA = "KEY_OPPOSING_USER_DATA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun onStart() {
        super.onStart()
        val myList = intent.getStringArrayListExtra(KEY_MY_DATA)
        val ouid = intent.getStringExtra(KEY_ACCESS_ID)
        val squa = SquadFragment()
        val bundle = Bundle()
        bundle.putStringArrayList(KEY_MY_DATA, myList)
        bundle.putString(KEY_ACCESS_ID, ouid)
        squa.arguments = bundle
        FragmentUtils().loadFragment(
            squa,
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