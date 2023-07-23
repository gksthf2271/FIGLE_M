package com.khs.data.nexon_api.response

import com.google.gson.annotations.SerializedName

data class UserMatchIdResponse(
    @SerializedName("accessIdList")
    val accessIdList: List<String>
)