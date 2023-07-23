package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class MatchDetailDTO (
    @SerializedName("seasonId")
    val seasonId: Int,
    @SerializedName("matchResult")
    var matchResult: String,
    @SerializedName("matchEndType")
    val matchEndType: Int,
    @SerializedName("systemPause")
    val systemPause: Int,
    @SerializedName("foul")
    val foul: Int,
    @SerializedName("injury")
    val injury: Int,
    @SerializedName("redCards")
    val redCards: Int,
    @SerializedName("yellowCards")
    val yellowCards: Int,
    @SerializedName("dribble")
    val dribble: Int,
    @SerializedName("cornerKick")
    val cornerKick: Int,
    @SerializedName("possession")
    val possession: Int
)