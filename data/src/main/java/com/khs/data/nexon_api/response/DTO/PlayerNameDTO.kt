package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class PlayerNameDTO(
    @SerializedName("id")
    val playerId: Long,
    @SerializedName("name")
    val playerName: String
)
