package com.khs.data.nexon_api.response

import com.google.gson.annotations.SerializedName

data class UserCareerHighResponse(
    @SerializedName("matchType")
    val matchType: Int,
    @SerializedName("division")
    val division: Int,
    @SerializedName("achievementDate")
    val achievementDate: String
)