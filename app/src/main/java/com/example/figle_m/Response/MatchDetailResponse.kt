package com.example.figle_m.Response

import com.example.figle_m.Response.DTO.MatchInfoDTO
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MatchDetailResponse: BaseResponse, Serializable {
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
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------MatchDetailResponse--------------------\n")
        sb.append(" matchId                     : $matchId\n")
        sb.append(" matchDate                   : $matchDate\n")
        sb.append(" matchType                   : $matchType\n")
        for (matchInfo in matchInfoList!!.iterator()){
            sb.append("${matchInfo.toString()}")
        }
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}