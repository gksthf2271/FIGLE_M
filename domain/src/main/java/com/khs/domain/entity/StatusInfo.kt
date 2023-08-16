package com.khs.domain.entity

data class StatusInfo(
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
)
