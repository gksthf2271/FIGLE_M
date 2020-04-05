package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusDTO (
    val shoot: Int,
    val effectiveShoot: Int,
    val assist: Int,
    val goal: Int,
    val dribble: Int,
    val passTry: Int,
    val passSuccess: Int,
    val block: Int,
    val tackle: Int,
    val spRating: Float
) : Parcelable