package com.khs.figle_m.common.util

import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.MatchInfo

object UserSortUtils {
    fun sortUserList(searchAccessId: String, matchInfo: Match): Pair<MatchInfo, MatchInfo> {
        when (searchAccessId) {
            matchInfo.matchInfo[0].accessId -> {
                return Pair(matchInfo.matchInfo[0], matchInfo.matchInfo[1])
            }
            matchInfo.matchInfo[1].accessId -> {
                return Pair(matchInfo.matchInfo[1], matchInfo.matchInfo[0])
            }
        }
        return Pair(matchInfo.matchInfo[0], matchInfo.matchInfo[1])
    }
}
