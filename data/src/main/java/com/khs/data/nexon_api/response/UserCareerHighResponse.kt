package com.khs.data.nexon_api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserCareerHighResponse(
    @Json(name = "matchType") val matchType: Int,
    @Json(name = "division") val division: Int,
    @Json(name = "achievementDate") val achievementDate: String
)