package com.example.figle_m.Response

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserHighRankResponse: BaseResponse, Serializable {
    private val TAG : String = javaClass.name

    @SerializedName("matchType")
    var matchType: Int? = null

    @SerializedName("division")
    var division: Int? = null

    @SerializedName("achievementDate")
    var achievementDate: String? = null


    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------UserHighRankResponse--------------------\n")
        sb.append(" matchType                   : $matchType\n")
        sb.append(" division                    : $division\n")
        sb.append(" achievementDate             : $achievementDate\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}
