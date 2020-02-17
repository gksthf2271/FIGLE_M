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
        val string: String = "passTry                     : $passTry \n" +
                "passSuccess                : $passSuccess \n" +
                "shortPassTry               : $shortPassTry \n" +
                "shortPassSuccess           : $shortPassSuccess \n" +
                "longPassTry                : $longPassTry \n" +
                "longPassSuccess            : $longPassSuccess \n" +
                "bouncingLobPassTry         : $bouncingLobPassTry \n" +
                "bouncingLobPassSuccess     : $bouncingLobPassSuccess \n" +
                "drivenGroundPassTry        : $drivenGroundPassTry \n" +
                "drivenGroundPassSuccess    : $drivenGroundPassSuccess \n" +
                "throughPassTry             : $throughPassTry \n" +
                "throughPassSuccess         : $throughPassSuccess \n" +
                "lobbedThroughPassTry       : $lobbedThroughPassTry \n" +
                "lobbedThroughPassSuccess   : $lobbedThroughPassSuccess \n"
        return string
    }
}