package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class ShootDetailDTO(
    @SerializedName("goalTime")
    val goalTime: Int,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("type")
    val type: Int,
    @SerializedName("result")
    val result: Int,
    @SerializedName("assist")
    val assist: Boolean,
    @SerializedName("hitPost")
    val hitPost: Boolean,
    @SerializedName("inPenalty")
    val inPenalty: Boolean
)