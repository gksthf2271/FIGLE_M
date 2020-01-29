package com.example.figle_m.Response

import android.util.Log
import com.google.gson.annotations.SerializedName

class UserResponse {
    private val TAG : String = javaClass.name

    @SerializedName("accessId")
    var accessId: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("level")
    var level: String? = null

    override fun toString(): String {
        Log.v(TAG,"accessId             : $accessId, " +
                "nickname             : $nickname, " +
                "level                   : $level, "
        )
        return super.toString()
    }
}