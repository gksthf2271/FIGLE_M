package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SeasonDTO(
    var dataVersion : String = "",
    var lastModified : Long = 0,
    var contentsLength : String = "",
    val seasonList: List<SeasonModel>
)

@JsonClass(generateAdapter = true)
data class SeasonModel(
    @Json(name = "seasonId") val seasonId: Int,
    @Json(name = "className") val className: String,
    @Json(name = "seasonImg") val seasonImg: String
)