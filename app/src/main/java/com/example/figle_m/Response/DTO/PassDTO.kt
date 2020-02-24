package com.example.figle_m.Response.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassDTO(
    val passTry: Int,
    val passSuccess: String,
    val shortPassTry: Int,
    val shortPassSuccess: Int,
    val longPassTry: Int,
    val longPassSuccess: Int,
    val bouncingLobPassTry: Int,
    val bouncingLobPassSuccess: Int,
    val drivenGroundPassTry: Int,
    val drivenGroundPassSuccess: Int,
    val throughPassTry: Int,
    val throughPassSuccess: Int,
    val lobbedThroughPassTry: Int,
    val lobbedThroughPassSuccess: Int
) : Parcelable