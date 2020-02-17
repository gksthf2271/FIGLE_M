package com.example.figle_m.Response

import android.util.Log
import com.example.figle_m.Response.DTO.MatchInfoDTO
import com.google.gson.annotations.SerializedName

class MatchDetailResponse {
    private val TAG: String = javaClass.name

    @SerializedName("matchId")
    var matchId: String? = null

    @SerializedName("matchDate")
    var matchDate: String? = null

    @SerializedName("matchType")
    var matchType: Int? = null

    @SerializedName("matchInfo")
    var matchInfo: List<MatchInfoDTO>? = null

    override fun toString(): String {
        val string: String = "matchId             : $matchId \n" +
                "matchDate           : $matchDate \n" +
                "matchType           : $matchType \n" +
                "matchInfo           : ${matchInfo.toString()} \n"
        return string
    }
}