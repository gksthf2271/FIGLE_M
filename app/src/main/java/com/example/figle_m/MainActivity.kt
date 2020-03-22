package com.example.figle_m

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.figle_m.SearchList.SearchListFragment
import com.example.figle_m.utils.FragmentUtils
import okhttp3.ResponseBody

class MainActivity : BaseActivity(), InitContract.View{
    val TAG: String = javaClass.name
    open val PREF_NAME = "playerNamePref"
    lateinit var mInitPresenter: InitPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        mInitPresenter!!.takeView(this)
        mInitPresenter.getPlayerNameList(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        mInitPresenter!!.dropView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getCurrentFragment() is SearchListFragment)
                FragmentUtils().loadFragment(HomeFragment(),R.id.fragment_container, supportFragmentManager)
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    fun getCurrentFragment(): Fragment {
        return FragmentUtils().currentFragment(supportFragmentManager!!, R.id.fragment_container)!!
    }

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
    }

    override fun showMainActivity(responseBody: ResponseBody) {
        Log.v(TAG,"showMainActivity(...)")
        val fm: FragmentManager = this.supportFragmentManager
        val homeFragment: HomeFragment = HomeFragment.getInstance()
        FragmentUtils().loadFragment(homeFragment, R.id.fragment_container,fm)
    }

    override fun showError(error: String) {
        Log.v(TAG,"showError : $error")
    }

    override fun initPresenter() {
        mInitPresenter = InitPresenter()
    }
}
