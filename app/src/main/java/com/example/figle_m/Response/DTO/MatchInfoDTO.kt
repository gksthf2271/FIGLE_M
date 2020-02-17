package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class MatchInfoDTO {
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
        val string : String =
            "accessId              : $accessId, \n" +
                       "nickname              : $nickname \n" +
                       "matchDetail           : ${matchDetail.toString()} \n" +
                       "shoot                 : ${shoot.toString()} \n" +
                       "shootDetail           : ${shootDetail.toString()} \n" +
                       "pass                  : $pass \n" +
                       "defence               : $defence \n" +
                       "player                : ${player.toString()} \n"

        return string
    }
}