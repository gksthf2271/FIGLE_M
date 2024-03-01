package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DefenceDTO(
    @Json(name = "blockTry") val blockTry: Int,
    @Json(name = "blockSuccess") val blockSuccess: Int,
    @Json(name = "tackleTry") val tackleTry: Int,
    @Json(name = "tackleSuccess") val tackleSuccess: Int
)