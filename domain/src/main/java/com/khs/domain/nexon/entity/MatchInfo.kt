package com.khs.domain.nexon.entity

data class MatchInfo(
    val accessId: String,
    val nickname: String,
    val matchDetailInfo: MatchDetailInfo,
    val shoot: ShootInfo,
    val shootDetail: List<ShootDetailInfo>,
    val pass: PassInfo,
    val defence: DefenceInfo,
    val player: List<Player>
)
