package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatusDTO (
    @Json(name = "shoot") val shoot: Int,
    @Json(name = "effectiveShoot") val effectiveShoot: Int,
    @Json(name = "assist") val assist: Int,
    @Json(name = "goal") val goal: Int,
    @Json(name = "dribble") val dribble: Int,
    @Json(name = "passTry") val passTry: Int,
    @Json(name = "passSuccess") val passSuccess: Int,
    @Json(name = "block") val block: Int,
    @Json(name = "tackle") val tackle: Int,
    @Json(name = "spRating") val spRating: Float
)