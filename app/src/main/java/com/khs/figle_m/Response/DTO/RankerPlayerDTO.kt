package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import com.khs.data.nexon_api.response.DTO.RankerPlayerStatDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RankerPlayerDTO (
    val spId: Int,
    val spPosition: Int,
    val createDate: String,
    val status: RankerPlayerStatDTO
) : Parcelable