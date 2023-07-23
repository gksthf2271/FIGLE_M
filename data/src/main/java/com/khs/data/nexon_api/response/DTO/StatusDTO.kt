package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class StatusDTO (
    @SerializedName("shoot")
    val shoot: Int,
    @SerializedName("effectiveShoot")
    val effectiveShoot: Int,
    @SerializedName("assist")
    val assist: Int,
    @SerializedName("goal")
    val goal: Int,
    @SerializedName("dribble")
    val dribble: Int,
    @SerializedName("passTry")
    val passTry: Int,
    @SerializedName("passSuccess")
    val passSuccess: Int,
    @SerializedName("block")
    val block: Int,
    @SerializedName("tackle")
    val tackle: Int,
    @SerializedName("spRating")
    val spRating: Float
)