package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchInfoDTO(
    @Json(name = "ouid") val ouid: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "matchDetail") val matchDetail: MatchDetailDTO,
    @Json(name = "shoot") val shoot: ShootDTO,
    @Json(name = "shootDetail") val shootDetail: List<ShootDetailDTO>,
    @Json(name = "pass") val pass: PassDTO,
    @Json(name = "defence") val defence: DefenceDTO,
    @Json(name = "player") val player: List<PlayerDTO>
)