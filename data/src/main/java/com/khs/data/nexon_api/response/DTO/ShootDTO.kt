package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class ShootDTO (
    @SerializedName("shootTotal")
    val shootTotal: Int,
    @SerializedName("effectiveShootTotal")
    val effectiveShootTotal: String,
    @SerializedName("shootOutScore")
    val shootOutScore: Int,
    @SerializedName("goalTotal")
    val goalTotal: Int,
    @SerializedName("goalTotalDisplay")
    val goalTotalDisplay: Int,
    @SerializedName("ownGoal")
    val ownGoal: Int,
    @SerializedName("shootHeading")
    val shootHeading: Int,
    @SerializedName("goalHeading")
    val goalHeading: Int,
    @SerializedName("shootFreekick")
    val shootFreekick: Int,
    @SerializedName("goalFreekick")
    val goalFreekick: Int,
    @SerializedName("shootInPenalty")
    val shootInPenalty: Int,
    @SerializedName("goalInPenalty")
    val goalInPenalty: Int,
    @SerializedName("shootOutPenalty")
    val shootOutPenalty: Int,
    @SerializedName("goalOutPenalty")
    val goalOutPenalty: Int,
    @SerializedName("shootPenaltyKick")
    val shootPenaltyKick: Int,
    @SerializedName("goalPenaltyKick")
    val goalPenaltyKick: Int
)
