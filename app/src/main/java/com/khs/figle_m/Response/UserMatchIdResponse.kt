package com.khs.figle_m.Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserMatchIdResponse(
    val accessIdList: List<String>
) : Parcelable