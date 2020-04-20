package com.khs.figle_m.Response.customDTO

import android.os.Parcelable
import com.khs.figle_m.Response.DTO.PlayerDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerListDTO(
    var accessId : String,
    var playerList : List<PlayerDTO>
) : Parcelable