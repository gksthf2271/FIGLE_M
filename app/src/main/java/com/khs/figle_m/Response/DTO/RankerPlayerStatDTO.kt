package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RankerPlayerStatDTO (
    val shoot: Float,
    val effectiveShoot: Float,
    val assist: Float,
    val goal: Float,
    val dribble: Float,
    val passTry: Float,
    val passSuccess: Float,
    val block: Float,
    val tackle: Float,
    val matchCount: Int
) : Parcelable