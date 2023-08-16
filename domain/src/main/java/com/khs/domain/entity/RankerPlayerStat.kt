package com.khs.domain.entity

data class RankerPlayerStat(
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
)
