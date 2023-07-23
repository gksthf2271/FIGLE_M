package com.khs.figle_m.Response.CustomDTO

import android.os.Parcelable
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerListDTO(
    var accessId : String,
    var playerList : List<PlayerDTO>
) : Parcelable