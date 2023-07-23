package com.khs.data.nexon_api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("accessId")
    val accessId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("teamPrice")
    var teamPrice : String
)