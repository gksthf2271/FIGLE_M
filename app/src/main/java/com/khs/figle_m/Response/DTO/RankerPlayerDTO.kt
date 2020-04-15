package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RankerPlayerDTO (
    val spId: Int,
    val spPosition: Int,
    val createDate: String,
    val status: RankerPlayerStatDTO
) : Parcelable