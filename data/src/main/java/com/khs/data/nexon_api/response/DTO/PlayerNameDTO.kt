package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName
data class PlayerNameDTO(
    var dataVersion : String = "",
    var lastModified : String = "",
    var contentsLength : String = "",
    var playernames : List<PlayerName>
)

data class PlayerName(
    @SerializedName("id")
    val playerId: Long,
    @SerializedName("name")
    val playerName: String
)