package com.example.figle_m.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

open class SharedPreferenceUtil{

    constructor()

    open fun createPref(context: Context, prefName: String) {

    }

    open fun savePref(context: Context, prefName: String, key:String, value:String){
        val settings: SharedPreferences = context.getSharedPreferences(prefName, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    open fun removePref(context: Context, prefName: String, key: String, allRemove: Boolean) {
        val settings: SharedPreferences = context.getSharedPreferences(prefName, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()
        when(allRemove) {
            true -> editor.clear()        // 전부 삭제
            false -> editor.remove(key)    // 특정 데이터 삭제
        }
        editor.apply()
    }

    open fun getPref(context: Context, prefName: String, key: String) : String{
        val settings: SharedPreferences = context.getSharedPreferences(prefName, MODE_PRIVATE)
        settings ?: return ""
        return settings.getString(key, "")!!
    }

}