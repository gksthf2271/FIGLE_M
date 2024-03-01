package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class PlayerNameDTO(
    var dataVersion : String = "",
    var lastModified : Long = 0,
    var contentsLength : String = "",
    var playernames : List<PlayerModel>
)

@JsonClass(generateAdapter = true)
data class PlayerModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
)