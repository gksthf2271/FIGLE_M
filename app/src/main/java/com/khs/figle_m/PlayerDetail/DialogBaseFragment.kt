package com.khs.figle_m.PlayerDetail

import android.util.Log
import androidx.fragment.app.DialogFragment

abstract class DialogBaseFragment : DialogFragment() {
    private val TAG: String = javaClass.name
    override fun onStart() {
        super.onStart()
        Log.v(TAG,"onStart(...)")
        initPresenter()
    }

    abstract fun initPresenter()
}