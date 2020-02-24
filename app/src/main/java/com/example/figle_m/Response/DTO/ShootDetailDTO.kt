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
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------ShootDetailDTO--------------------\n")
        sb.append(" goalTime                     : $goalTime\n")
        sb.append(" x                            : $x\n")
        sb.append(" y                            : $y\n")
        sb.append(" type                         : $type\n")
        sb.append(" result                       : $result\n")
        sb.append(" assist                       : $assist\n")
        sb.append(" hitPost                      : $hitPost\n")
        sb.append(" inPenalty                    : $inPenalty\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}