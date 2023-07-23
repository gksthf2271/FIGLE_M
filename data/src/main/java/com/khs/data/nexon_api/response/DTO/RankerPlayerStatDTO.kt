package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class RankerPlayerStatDTO (
    @SerializedName("shoot")
    val shoot: Float,
    @SerializedName("effectiveShoot")
    val effectiveShoot: Float,
    @SerializedName("assist")
    val assist: Float,
    @SerializedName("goal")
    val goal: Float,
    @SerializedName("dribble")
    val dribble: Float,
    @SerializedName("passTry")
    val passTry: Float,
    @SerializedName("passSuccess")
    val passSuccess: Float,
    @SerializedName("block")
    val block: Float,
    @SerializedName("tackle")
    val tackle: Float,
    @SerializedName("matchCount")
    val matchCount: Int
)