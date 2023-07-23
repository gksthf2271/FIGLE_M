package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName

data class PassDTO(
    @SerializedName("passTry")
    val passTry: Int,
    @SerializedName("passSuccess")
    val passSuccess: Int,
    @SerializedName("shortPassTry")
    val shortPassTry: Int,
    @SerializedName("shortPassSuccess")
    val shortPassSuccess: Int,
    @SerializedName("longPassTry")
    val longPassTry: Int,
    @SerializedName("longPassSuccess")
    val longPassSuccess: Int,
    @SerializedName("bouncingLobPassTry")
    val bouncingLobPassTry: Int,
    @SerializedName("bouncingLobPassSuccess")
    val bouncingLobPassSuccess: Int,
    @SerializedName("drivenGroundPassTry")
    val drivenGroundPassTry: Int,
    @SerializedName("drivenGroundPassSuccess")
    val drivenGroundPassSuccess: Int,
    @SerializedName("throughPassTry")
    val throughPassTry: Int,
    @SerializedName("throughPassSuccess")
    val throughPassSuccess: Int,
    @SerializedName("lobbedThroughPassTry")
    val lobbedThroughPassTry: Int,
    @SerializedName("lobbedThroughPassSuccess")
    val lobbedThroughPassSuccess: Int
)