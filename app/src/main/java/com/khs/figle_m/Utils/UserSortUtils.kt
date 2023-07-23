package com.khs.figle_m.Utils

import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.MatchDetailResponse

object UserSortUtils {
    fun sortUserList(searchAccessId:String, matchInfo: MatchDetailResponse) : Pair<MatchInfoDTO, MatchInfoDTO> {
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