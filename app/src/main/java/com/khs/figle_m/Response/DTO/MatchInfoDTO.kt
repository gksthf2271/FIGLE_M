package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchInfoDTO(
    val accessId: String,
    val nickname: String,
    val matchDetail: MatchDetailDTO,
    val shoot: ShootDTO,
    val shootDetail: List<ShootDetailDTO>,
    val pass: PassDTO,
    val defence: DefenceDTO,
    val player: List<PlayerDTO>
) : Parcelable