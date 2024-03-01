package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchDetailDTO (
    @Json(name = "seasonId") val seasonId: Int,
    @Json(name = "matchResult") var matchResult: String,
    @Json(name = "matchEndType") val matchEndType: Int,
    @Json(name = "systemPause") val systemPause: Int,
    @Json(name = "foul") val foul: Int,
    @Json(name = "injury") val injury: Int,
    @Json(name = "redCards") val redCards: Int,
    @Json(name = "yellowCards") val yellowCards: Int,
    @Json(name = "dribble") val dribble: Int,
    @Json(name = "cornerKick") val cornerKick: Int,
    @Json(name = "possession") val possession: Int
)