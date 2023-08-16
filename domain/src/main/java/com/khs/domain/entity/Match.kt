package com.khs.domain.entity

data class Match(
    val matchId: String,
    var matchDate: String,
    val matchType: Int,
    val matchInfo: List<MatchInfo>
)
