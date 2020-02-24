package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class ShootDTO {
    private val TAG: String = javaClass.name

    @SerializedName("shootTotal")
    var shootTotal: Int? = null

    @SerializedName("effectiveShootTotal")
    var effectiveShootTotal: String? = null

    @SerializedName("shootOutScore")
    var shootOutScore: Int? = null

    @SerializedName("goalTotal")
    var goalTotal: Int? = null

    @SerializedName("goalTotalDisplay")
    var goalTotalDisplay: Int? = null

    @SerializedName("ownGoal")
    var ownGoal: Int? = null

    @SerializedName("shootHeading")
    var shootHeading: Int? = null

    @SerializedName("goalHeading")
    var goalHeading: Int? = null

    @SerializedName("shootFreekick")
    var shootFreekick: Int? = null

    @SerializedName("goalFreekick")
    var goalFreekick: Int? = null

    @SerializedName("shootInPenalty")
    var shootInPenalty: Int? = null

    @SerializedName("goalInPenalty")
    var goalInPenalty: Int? = null

    @SerializedName("shootOutPenalty")
    var shootOutPenalty: Int? = null

    @SerializedName("goalOutPenalty")
    var goalOutPenalty: Int? = null

    @SerializedName("shootPenaltyKick")
    var shootPenaltyKick: Int? = null

    @SerializedName("goalPenaltyKick")
    var goalPenaltyKick: Int? = null


    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("\n------------------------------------------------\n")
        sb.append("------------------ShootDTO--------------------\n")
        sb.append(" shootTotal                     : $shootTotal\n")
        sb.append(" effectiveShootTotal            : $effectiveShootTotal\n")
        sb.append(" shootOutScore                  : $shootOutScore\n")
        sb.append(" goalTotal                      : $goalTotal\n")
        sb.append(" goalTotalDisplay               : $goalTotalDisplay\n")
        sb.append(" ownGoal                        : $ownGoal\n")
        sb.append(" shootHeading                   : $shootHeading\n")
        sb.append(" goalHeading                    : $goalHeading\n")
        sb.append(" shootFreekick                  : $shootFreekick\n")
        sb.append(" goalFreekick                   : $goalFreekick\n")
        sb.append(" shootInPenalty                 : $shootInPenalty\n")
        sb.append(" goalInPenalty                  : $goalInPenalty\n")
        sb.append(" shootOutPenalty                : $shootOutPenalty\n")
        sb.append(" goalOutPenalty                 : $goalOutPenalty\n")
        sb.append(" shootPenaltyKick               : $shootPenaltyKick\n")
        sb.append(" goalPenaltyKick                : $goalPenaltyKick\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}