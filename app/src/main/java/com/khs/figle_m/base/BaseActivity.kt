package com.khs.figle_m.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.khs.figle_m.utils.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onSaveInstanceState(...) : $outState")
    }

    abstract fun initPresenter()
}
