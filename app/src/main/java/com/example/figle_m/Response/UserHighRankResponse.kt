package com.example.figle_m.Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserHighRankResponse(
    val matchType: Int,
    val division: Int,
    val achievementDate: String
) : Parcelable