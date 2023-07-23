package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class MatchInfoDTO(
    @SerializedName("accessId")
    val accessId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("matchDetail")
    val matchDetail: MatchDetailDTO,
    @SerializedName("shoot")
    val shoot: ShootDTO,
    @SerializedName("shootDetail")
    val shootDetail: List<ShootDetailDTO>,
    @SerializedName("pass")
    val pass: PassDTO,
    @SerializedName("defence")
    val defence: DefenceDTO,
    @SerializedName("player")
    val player: List<PlayerDTO>
)