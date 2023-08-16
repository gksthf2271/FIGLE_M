package com.khs.figle_m.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khs.figle_m.utils.LogUtil

abstract class BaseFragment: Fragment(){
    private val TAG: String = javaClass.simpleName
    var isRestartApp: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onCreateView(...)")
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

    override fun onStop() {
        super.onStop()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onStop(...)")
        isRestartApp = true
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onStop(...)")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onDestroyView(...)")
        isRestartApp = false
    }

    abstract fun initPresenter()
}