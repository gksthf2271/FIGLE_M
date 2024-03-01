package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShootDetailDTO(
    @Json(name = "goalTime") val goalTime: Int,
    @Json(name = "x") val x: Double,
    @Json(name = "y") val y: Double,
    @Json(name = "type") val type: Int,
    @Json(name = "result") val result: Int,
    @Json(name = "assist") val assist: Boolean,
    @Json(name = "hitPost") val hitPost: Boolean,
    @Json(name = "inPenalty") val inPenalty: Boolean
)