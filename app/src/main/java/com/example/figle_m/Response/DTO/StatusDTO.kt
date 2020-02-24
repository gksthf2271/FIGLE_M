package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class StatusDTO {
    private val TAG : String = javaClass.name

    @SerializedName("shoot")
    var shoot: Int? = null

    @SerializedName("effectiveShoot")
    var effectiveShoot: String? = null

    @SerializedName("assist")
    var assist: Int? = null

    @SerializedName("goal")
    var goal: Int? = null

    @SerializedName("dribble")
    var dribble: Int? = null

    @SerializedName("passTry")
    var passTry: Int? = null

    @SerializedName("passSuccess")
    var passSuccess: Int? = null

    @SerializedName("block")
    var block: Int? = null

    @SerializedName("tackle")
    var tackle: Int? = null

    @SerializedName("spRating")
    var spRating: Float? = null


    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------StatusDTO--------------------\n")
        sb.append(" shoot                     : $shoot\n")
        sb.append(" effectiveShoot            : $effectiveShoot\n")
        sb.append(" assist                    : $assist\n")
        sb.append(" goal                      : $goal\n")
        sb.append(" dribble                   : $dribble\n")
        sb.append(" passTry                   : $passTry\n")
        sb.append(" passSuccess               : $passSuccess\n")
        sb.append(" block                     : $block\n")
        sb.append(" tackle                    : $tackle\n")
        sb.append(" spRating                  : $spRating\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}