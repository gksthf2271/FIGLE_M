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
        val string: String = "shoot                : $shoot \n" +
                        "effectiveShoot       : $effectiveShoot \n" +
                        "assist               : $assist \n" +
                        "goal                 : $goal \n" +
                        "dribble              : $dribble \n" +
                        "passTry              : $passTry \n" +
                        "passSuccess          : $passSuccess \n" +
                        "block                : $block \n" +
                        "tackle               : $tackle \n" +
                        "spRating             : $spRating \n"
        return string
    }
}