package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class ShootDetailDTO {
    private val TAG: String = javaClass.name

    @SerializedName("goalTime")
    var goalTime: Int? = null

    @SerializedName("x")
    var x: Double? = null

    @SerializedName("y")
    var y: Double? = null

    @SerializedName("type")
    var type: Int? = null

    @SerializedName("result")
    var result: Int? = null

    @SerializedName("assist")
    var assist: Boolean? = null

    @SerializedName("hitPost")
    var hitPost: Boolean? = null

    @SerializedName("inPenalty")
    var inPenalty: Boolean? = null

    override fun toString(): String {
        val string: String = "goalTime             : $goalTime \n" +
                "x                    : $x \n" +
                "y                    : $y \n" +
                "type                 : $type \n" +
                "result               : $result \n" +
                "assist               : $assist \n" +
                "hitPost              : $hitPost \n" +
                "inPenalty            : $inPenalty \n"
        return string
    }
}