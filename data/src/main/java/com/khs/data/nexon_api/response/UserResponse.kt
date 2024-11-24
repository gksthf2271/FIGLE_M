package com.khs.data.nexon_api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "ouid") val ouid: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "level") val level: String,
    @Json(name = "teamPrice") var teamPrice : String
): Serializable