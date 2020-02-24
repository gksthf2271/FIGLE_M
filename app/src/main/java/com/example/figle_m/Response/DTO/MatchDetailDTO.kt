package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class MatchDetailDTO {
    private val TAG: String = javaClass.name

    @SerializedName("seasonId")
    var seasonId: Int? = null

    @SerializedName("matchResult")
    var matchResult: String? = null

    @SerializedName("matchEndType")
    var matchEndType: Int? = null

    @SerializedName("systemPause")
    var systemPause: Int? = null

    @SerializedName("foul")
    var foul: Int? = null

    @SerializedName("injury")
    var injury: Int? = null

    @SerializedName("redCards")
    var redCards: Int? = null

    @SerializedName("yellowCards")
    var yellowCards: Int? = null

    @SerializedName("dribble")
    var dribble: Int? = null

    @SerializedName("cornerKick")
    var cornerKick: Int? = null

    @SerializedName("possession")
    var possession: Int? = null

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("\n------------------------------------------------\n")
        sb.append("------------------MatchDetailDTO--------------------\n")
        sb.append(" seasonId                     : $seasonId\n")
        sb.append(" matchResult                  : $matchResult\n")
        sb.append(" matchEndType                 : $matchEndType\n")
        sb.append(" systemPause                  : $systemPause\n")
        sb.append(" foul                         : $foul\n")
        sb.append(" injury                       : $injury\n")
        sb.append(" redCards                     : $redCards\n")
        sb.append(" yellowCards                  : $yellowCards\n")
        sb.append(" dribble                      : $dribble\n")
        sb.append(" cornerKick                   : $cornerKick\n")
        sb.append(" matchEndType                 : $matchEndType\n")
        sb.append(" possession                   : $possession\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}