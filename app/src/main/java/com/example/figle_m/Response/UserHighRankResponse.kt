package com.example.figle_m.Response

import android.util.Log
import com.google.gson.annotations.SerializedName

class UserHighRankResponse: BaseResponse {
    private val TAG : String = javaClass.name

    @SerializedName("matchType")
    var matchType: Int? = null

    @SerializedName("division")
    var division: Int? = null

    @SerializedName("achievementDate")
    var achievementDate: String? = null

    override fun toString(): String {
        val string: String = "accessId             : $matchType \n" +
                        "nickname             : $division \n" +
                        "level                : $achievementDate \n"

        return string
    }
}
