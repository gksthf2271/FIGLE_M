package com.khs.figle_m.utils

import android.util.Log

object LogUtil {
    val TAG_UI = "FIGLE_UI"
    val TAG_SETUP = "FIGLE_SETUP"
    val TAG_NETWORK = "FIGLE_NETWORK"
    val TAG_SEARCH = "FIGLE_SEARCH"
    val TAG_RANK = "FIGLE_MEMBER"
    val TAG_TRADE = "FIGLE_TRADE"
    val TAG_DEFAULT = "FIGLE"


    fun iLog(tag: String?, classTag: String, msg: String) {
        val localTAG = tag ?: TAG_DEFAULT
        Log.i(localTAG, "$classTag : $msg")
    }

    fun dLog(tag: String?, classTag: String, msg: String) {
        val localTAG = tag ?: TAG_DEFAULT
        Log.d(localTAG, "$classTag : $msg")
    }

    fun eLog(tag: String?, classTag: String, msg: String) {
        val localTAG = tag ?: TAG_DEFAULT
        Log.e(localTAG, "$classTag : $msg")
    }

    fun vLog(tag: String?, classTag: String, msg: String) {
        val localTAG = tag ?: TAG_DEFAULT
        Log.v(localTAG, "$classTag : $msg")
    }

    fun wLog(tag: String?, classTag: String, msg: String) {
        val localTAG = tag ?: TAG_DEFAULT
        Log.w(localTAG, "$classTag : $msg")
    }
}