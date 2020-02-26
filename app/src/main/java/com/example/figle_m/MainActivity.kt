package com.example.figle_m

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.figle_m.SearchList.SearchListFragment
import com.example.figle_m.utils.FragmentUtils

class MainActivity : BaseActivity() {
    override fun initPresenter() {
       HomeFragment().initPresenter()
    }

    val TAG: String = javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val fm: FragmentManager = this.supportFragmentManager
        val homeFragment: HomeFragment = HomeFragment.getInstance()
        FragmentUtils().loadFragment(homeFragment, R.id.fragment_container,fm)
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
}
