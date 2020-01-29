package com.example.figle_m

import android.os.Bundle
import androidx.fragment.app.FragmentManager
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
}
