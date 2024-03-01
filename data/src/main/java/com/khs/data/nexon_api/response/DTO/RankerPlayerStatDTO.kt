package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RankerPlayerStatDTO (
    @Json(name = "shoot") val shoot: Float,
    @Json(name = "effectiveShoot") val effectiveShoot: Float,
    @Json(name = "assist") val assist: Float,
    @Json(name = "goal") val goal: Float,
    @Json(name = "dribble") val dribble: Float,
    @Json(name = "passTry") val passTry: Float,
    @Json(name = "passSuccess") val passSuccess: Float,
    @Json(name = "block") val block: Float,
    @Json(name = "tackle") val tackle: Float,
    @Json(name = "matchCount") val matchCount: Int
)