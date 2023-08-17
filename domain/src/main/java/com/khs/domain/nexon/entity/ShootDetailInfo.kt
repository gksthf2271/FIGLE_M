package com.khs.domain.nexon.entity

data class ShootDetailInfo(
    val goalTime: Int,
    val x: Double,
    val y: Double,
    val type: Int,
    val result: Int,
    val assist: Boolean,
    val hitPost: Boolean,
    val inPenalty: Boolean
)
