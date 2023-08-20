package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class SeasonDTO(
    @SerializedName("seasonId")
    val seasonId: Int,
    @SerializedName("className")
    val className: String,
    @SerializedName("seasonImg")
    val seasonImg: String
)