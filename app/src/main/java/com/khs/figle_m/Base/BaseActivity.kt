package com.khs.figle_m.Base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khs.figle_m.Utils.LogUtil

abstract class BaseActivity: AppCompatActivity() {
    private val TAG:String = javaClass.simpleName
    var isRestartApp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRestartApp = false
    }

    override fun onStart() {
        super.onStart()
        if (!isRestartApp) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"onStart(...)")
            initPresenter()
        } else {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"RestartApp!")
        }
    }

    override fun onRestart() {
        super.onRestart()
        isRestartApp = true
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onRestart(...) : $isRestartApp")
    }

    abstract fun initPresenter()
}
