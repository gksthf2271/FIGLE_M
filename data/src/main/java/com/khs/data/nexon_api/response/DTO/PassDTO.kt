package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PassDTO(
    @Json(name = "passTry") val passTry: Int,
    @Json(name = "passSuccess") val passSuccess: Int,
    @Json(name = "shortPassTry") val shortPassTry: Int,
    @Json(name = "shortPassSuccess") val shortPassSuccess: Int,
    @Json(name = "longPassTry") val longPassTry: Int,
    @Json(name = "longPassSuccess") val longPassSuccess: Int,
    @Json(name = "bouncingLobPassTry") val bouncingLobPassTry: Int,
    @Json(name = "bouncingLobPassSuccess") val bouncingLobPassSuccess: Int,
    @Json(name = "drivenGroundPassTry") val drivenGroundPassTry: Int,
    @Json(name = "drivenGroundPassSuccess") val drivenGroundPassSuccess: Int,
    @Json(name = "throughPassTry") val throughPassTry: Int,
    @Json(name = "throughPassSuccess") val throughPassSuccess: Int,
    @Json(name = "lobbedThroughPassTry") val lobbedThroughPassTry: Int,
    @Json(name = "lobbedThroughPassSuccess") val lobbedThroughPassSuccess: Int
)