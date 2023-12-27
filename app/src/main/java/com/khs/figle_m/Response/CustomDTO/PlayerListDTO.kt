package com.khs.figle_m.Response.CustomDTO

import android.os.Parcelable
import com.khs.figle_m.Response.DTO.PlayerDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerListDTO(
    var ouid : String,
    var playerList : List<PlayerDTO>
) : Parcelable