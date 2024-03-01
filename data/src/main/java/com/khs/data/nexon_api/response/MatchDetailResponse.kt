package com.khs.data.nexon_api.response

import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchDetailResponse(
    @Json(name = "matchId") val matchId: String,
    @Json(name = "matchDate") var matchDate: String,
    @Json(name = "matchType") val matchType: Int,
    @Json(name = "matchInfo") val matchInfo: List<MatchInfoDTO>
)