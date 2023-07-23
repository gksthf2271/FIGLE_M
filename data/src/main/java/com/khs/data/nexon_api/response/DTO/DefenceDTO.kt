package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class DefenceDTO(
    @SerializedName("blockTry")
    val blockTry: Int,
    @SerializedName("blockSuccess")
    val blockSuccess: Int,
    @SerializedName("tackleTry")
    val tackleTry: Int,
    @SerializedName("tackleSuccess")
    val tackleSuccess: Int
)