package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class MatchInfoDTO{
    private val TAG: String = javaClass.name

    @SerializedName("accessId")
    var accessId: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("matchDetail")
    var matchDetail: MatchDetailDTO? = null

    @SerializedName("shoot")
    var shoot: ShootDTO? = null

    @SerializedName("shootDetail")
    var shootDetail: List<ShootDetailDTO>? = null

    @SerializedName("pass")
    var pass: PassDTO? = null

    @SerializedName("defence")
    var defence: DefenceDTO? = null

    @SerializedName("player")
    var player: List<PlayerDTO>? = null

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------MatchInfoDTO--------------------\n")
        sb.append(" accessId                    : $accessId\n")
        sb.append(" nickname                    : $nickname\n")
        sb.append(" matchDetail                 : ${matchDetail.toString()}\n")
        sb.append(" shoot                       : $shoot\n")
        for (shootDetail in shootDetail!!.iterator()) {
            sb.append("${shootDetail.toString()}")
        }
        sb.append(" pass                        : $pass\n")
        sb.append(" defence                     : ${defence.toString()}\n")
        for (player in player!!.iterator()) {
            sb.append("${player.toString()}")
        }
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}