package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchDetailDTO (
    val seasonId: Int,
    var matchResult: String,
    val matchEndType: Int,
    val systemPause: Int,
    val foul: Int,
    val injury: Int,
    val redCards: Int,
    val yellowCards: Int,
    val dribble: Int,
    val cornerKick: Int,
    val possession: Int
) : Parcelable