package com.example.figle_m.Response

import com.example.figle_m.Response.DTO.MatchInfoDTO
import com.google.gson.annotations.SerializedName

class MatchDetailResponse: BaseResponse {
    private val TAG: String = javaClass.name

    @SerializedName("matchId")
    var matchId: String? = null

    @SerializedName("matchDate")
    var matchDate: String? = null

    @SerializedName("matchType")
    var matchType: Int? = null

    @SerializedName("matchInfo")
    var matchInfoList: List<MatchInfoDTO>? = null

    override fun toString(): String {
        var string: String = "matchId             : $matchId \n" +
                "matchDate           : $matchDate \n" +
                "matchType           : $matchType \n"
        for (matchInfo in matchInfoList!!.iterator()){
            string += matchInfo.toString()
        }
        return string
    }
}