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
        val string: String = "seasonId             : $seasonId \n" +
                "matchResult          : $matchResult \n" +
                "matchEndType         : $matchEndType \n" +
                "systemPause          : $systemPause \n" +
                "foul                 : $foul \n" +
                "injury               : $injury \n" +
                "redCards             : $redCards \n" +
                "yellowCards          : $yellowCards \n" +
                "dribble              : $dribble \n" +
                "cornerKick           : $cornerKick \n" +
                "matchEndType         : $matchEndType \n" +
                "possession           : $possession \n"
        return string
    }
}