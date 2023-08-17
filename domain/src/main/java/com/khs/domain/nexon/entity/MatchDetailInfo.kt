package com.khs.domain.nexon.entity

data class MatchDetailInfo(
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
)