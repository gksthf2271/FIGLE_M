package com.khs.data.nexon_api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserMatchIdResponse(
    @Json(name = "accessIdList") val accessIdList: List<String>
)