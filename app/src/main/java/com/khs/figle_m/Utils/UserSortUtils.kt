package com.khs.figle_m.Utils

import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse

class UserSortUtils() {
    open fun sortUserList(searchAccessId:String, matchInfo:MatchDetailResponse) : Pair<MatchInfoDTO, MatchInfoDTO> {
        when (searchAccessId) {
            matchInfo.matchInfo[0].ouid -> {
                return Pair(matchInfo.matchInfo[0], matchInfo.matchInfo[1])
            }
            matchInfo.matchInfo[1].ouid -> {
                return Pair(matchInfo.matchInfo[1], matchInfo.matchInfo[0])
            }
        }
        return Pair(matchInfo.matchInfo[0], matchInfo.matchInfo[1])
    }
}