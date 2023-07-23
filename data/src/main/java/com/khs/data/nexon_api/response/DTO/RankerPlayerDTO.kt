package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class RankerPlayerDTO (
    @SerializedName("spId")
    val spId: Int,
    @SerializedName("spPosition")
    val spPosition: Int,
    @SerializedName("createDate")
    val createDate: String,
    @SerializedName("status")
    val status: RankerPlayerStatDTO
)