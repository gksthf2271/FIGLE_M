package com.khs.figle_m.Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserIdResponse(
    val ouid: String
) : Parcelable

@Parcelize
data class UserResponse(
    val ouid: String,
    val nickname: String,
    val level: String,
    var teamPrice : String
) : Parcelable