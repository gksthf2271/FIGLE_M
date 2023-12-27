package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonIdDTO(
    val seasonId: Int,
    val className: Int,
    val seasonImg: String
) : Parcelable