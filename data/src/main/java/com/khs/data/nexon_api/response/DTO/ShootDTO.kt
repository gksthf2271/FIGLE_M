package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShootDTO (
    @Json(name = "shootTotal") val shootTotal: Int,
    @Json(name = "effectiveShootTotal") val effectiveShootTotal: String,
    @Json(name = "shootOutScore") val shootOutScore: Int,
    @Json(name = "goalTotal") val goalTotal: Int,
    @Json(name = "goalTotalDisplay") val goalTotalDisplay: Int,
    @Json(name = "ownGoal") val ownGoal: Int,
    @Json(name = "shootHeading") val shootHeading: Int,
    @Json(name = "goalHeading") val goalHeading: Int,
    @Json(name = "shootFreekick") val shootFreekick: Int,
    @Json(name = "goalFreekick") val goalFreekick: Int,
    @Json(name = "shootInPenalty") val shootInPenalty: Int,
    @Json(name = "goalInPenalty") val goalInPenalty: Int,
    @Json(name = "shootOutPenalty") val shootOutPenalty: Int,
    @Json(name = "goalOutPenalty") val goalOutPenalty: Int,
    @Json(name = "shootPenaltyKick") val shootPenaltyKick: Int,
    @Json(name = "goalPenaltyKick") val goalPenaltyKick: Int
)
