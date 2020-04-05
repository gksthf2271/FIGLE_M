package com.khs.figle_m.Base

import android.util.Log
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment(){
    private val TAG: String = javaClass.name
    override fun onStart() {
        super.onStart()
        Log.v(TAG,"onStart(...)")
        initPresenter()
    }

    abstract fun initPresenter()
}