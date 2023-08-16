package com.khs.domain.entity

data class PassInfo (
    val passTry: Int,
    val passSuccess: Int,
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
)