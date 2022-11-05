package com.khs.figle_m.Base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment(){
    private val TAG: String = javaClass.simpleName
    var isRestartApp: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        Log.v(TAG, "onCreateView(...)")
        isRestartApp = false
    }

    override fun onStart() {
        super.onStart()
        if (!isRestartApp) {
            Log.v(TAG, "onStart(...)")
            initPresenter()
        } else {
            Log.v(TAG, "RestartApp!")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"onStop(...)")
        isRestartApp = true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"onStop(...)")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(TAG,"onDestroyView(...)")
        isRestartApp = false
    }

    abstract fun initPresenter()
}