package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RankerPlayerDTO (
    @Json(name = "spId") val spId: Int,
    @Json(name = "spPosition") val spPosition: Int,
    @Json(name = "createDate") val createDate: String,
    @Json(name = "status") val status: RankerPlayerStatDTO
)