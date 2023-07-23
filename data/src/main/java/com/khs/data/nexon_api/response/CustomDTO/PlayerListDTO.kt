package com.khs.data.nexon_api.response.CustomDTO

import com.google.gson.annotations.SerializedName
import com.khs.data.nexon_api.response.DTO.PlayerDTO

data class PlayerListDTO(
    @SerializedName("accessId")
    var accessId : String,
    @SerializedName("playerList")
    var playerList : List<PlayerDTO>
)