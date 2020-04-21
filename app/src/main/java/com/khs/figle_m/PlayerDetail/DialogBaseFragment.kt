package com.khs.figle_m.PlayerDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khs.figle_m.MainActivity

abstract class DialogBaseFragment : DialogFragment() {
    private val TAG: String = javaClass.name
    var isRestartApp: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isRestartApp = false
        Log.v(TAG, "onCreateView(...) : $isRestartApp")
        return super.onCreateView(inflater, container, savedInstanceState)
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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(TAG,"onDestroyView(...)")
        isRestartApp = false
    }

    abstract fun initPresenter()
}