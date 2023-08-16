package com.khs.domain.entity

data class RankerPlayer(
    val spId: Int,
    val spPosition: Int,
    val createDate: String,
    val status: RankerPlayerStat
)
