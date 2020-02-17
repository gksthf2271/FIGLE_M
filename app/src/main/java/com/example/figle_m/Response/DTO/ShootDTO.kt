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
        val string: String = "shootTotal                     : $shootTotal \n" +
                "effectiveShootTotal            : $effectiveShootTotal \n" +
                "shootOutScore                  : $shootOutScore \n" +
                "goalTotal                      : $goalTotal \n" +
                "goalTotalDisplay               : $goalTotalDisplay \n" +
                "ownGoal                        : $ownGoal \n" +
                "shootHeading                   : $shootHeading \n" +
                "goalHeading                    : $goalHeading \n" +
                "shootFreekick                  : $shootFreekick \n" +
                "goalFreekick                   : $goalFreekick \n" +
                "shootInPenalty                 : $shootInPenalty \n" +
                "goalInPenalty                  : $goalInPenalty \n" +
                "shootOutPenalty                : $shootOutPenalty \n" +
                "goalOutPenalty                 : $goalOutPenalty \n" +
                "shootPenaltyKick               : $shootPenaltyKick \n" +
                "goalPenaltyKick                : $goalPenaltyKick \n "
        return string
    }
}