package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerDTO (
    val spId: Int,
    val spPosition: Int,
    val spGrade: Int,
    val status: StatusDTO
) : Parcelable