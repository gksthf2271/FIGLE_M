package com.example.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShootDetailDTO(
    val goalTime: Int,
    val x: Double,
    val y: Double,
    val type: Int,
    val result: Int,
    val assist: Boolean,
    val hitPost: Boolean,
    val inPenalty: Boolean
) : Parcelable