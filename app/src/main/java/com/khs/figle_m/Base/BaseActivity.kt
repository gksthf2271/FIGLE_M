package com.khs.figle_m.Base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    private val TAG:String = javaClass.name
    var isRestartApp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onRestart() {
        super.onRestart()
        isRestartApp = true
        Log.v(TAG,"onRestart(...) : $isRestartApp")
    }

    abstract fun initPresenter()
}
