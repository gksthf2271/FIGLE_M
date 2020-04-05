package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DefenceDTO(
    val blockTry: Int,
    val blockSuccess: Int,
    val tackleTry: Int,
    val tackleSuccess: Int
) : Parcelable