package com.khs.data.nexon_api.response.CustomDTO

import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerListDTO(
    @Json(name = "accessId") var accessId : String,
    @Json(name = "playerList") var playerList : List<PlayerDTO>
)