package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class PassDTO {
    private val TAG: String = javaClass.name

    @SerializedName("passTry")
    var passTry: Int? = null

    @SerializedName("passSuccess")
    var passSuccess: String? = null

    @SerializedName("shortPassTry")
    var shortPassTry: Int? = null

    @SerializedName("shortPassSuccess")
    var shortPassSuccess: Int? = null

    @SerializedName("longPassTry")
    var longPassTry: Int? = null

    @SerializedName("longPassSuccess")
    var longPassSuccess: Int? = null

    @SerializedName("bouncingLobPassTry")
    var bouncingLobPassTry: Int? = null

    @SerializedName("bouncingLobPassSuccess")
    var bouncingLobPassSuccess: Int? = null

    @SerializedName("drivenGroundPassTry")
    var drivenGroundPassTry: Int? = null

    @SerializedName("drivenGroundPassSuccess")
    var drivenGroundPassSuccess: Int? = null

    @SerializedName("throughPassTry")
    var throughPassTry: Int? = null

    @SerializedName("throughPassSuccess")
    var throughPassSuccess: Int? = null

    @SerializedName("lobbedThroughPassTry")
    var lobbedThroughPassTry: Int? = null

    @SerializedName("lobbedThroughPassSuccess")
    var lobbedThroughPassSuccess: Int? = null

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------PassDTO--------------------\n")
        sb.append(" passTry                    : $passTry\n")
        sb.append(" passSuccess                : $passSuccess\n")
        sb.append(" shortPassTry               : $shortPassTry\n")
        sb.append(" shortPassSuccess           : $shortPassSuccess\n")
        sb.append(" longPassTry                : $longPassTry\n")
        sb.append(" longPassSuccess            : $longPassSuccess\n")
        sb.append(" bouncingLobPassTry         : $bouncingLobPassTry\n")
        sb.append(" bouncingLobPassSuccess     : $bouncingLobPassSuccess\n")
        sb.append(" drivenGroundPassTry        : $drivenGroundPassTry\n")
        sb.append(" drivenGroundPassSuccess    : $drivenGroundPassSuccess\n")
        sb.append(" throughPassTry             : $throughPassTry\n")
        sb.append(" throughPassSuccess         : $throughPassSuccess\n")
        sb.append(" lobbedThroughPassTry       : $lobbedThroughPassTry\n")
        sb.append(" lobbedThroughPassSuccess   : $lobbedThroughPassSuccess\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}