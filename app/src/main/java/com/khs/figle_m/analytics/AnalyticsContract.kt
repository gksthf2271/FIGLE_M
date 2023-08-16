package com.khs.figle_m.analytics

import com.khs.figle_m.base.BasePresenter
import com.khs.figle_m.base.BaseView
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse

interface AnalyticsContract : BaseView {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading(isError: Boolean)

        fun showMatchDetail(matchDetailList: List<MatchDetailResponse>)
        fun showPlayerMap(playerMap: Map<Int, List<PlayerDTO>>)
        fun showPlayerInfoList(playerInfoList: List<AnalyticsPlayer>)
        fun showPlayerImage(playerInfoList: List<AnalyticsPlayer>)

        fun showPlayerDetailDialogFragment(playerDTO: PlayerDTO, rankerPlayerDTOList: List<RankerPlayerDTO>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadMatchDetail(matchIdList: List<String>)
        fun loadPlayerList(accessId: String, matchDetailList: List<MatchDetailResponse>)
        fun loadPlayerInfoList(playerMap: Map<Int, List<PlayerDTO>>)
        fun loadPlayerImageUrl(playerInfoList: List<AnalyticsPlayer>)
        fun loadRankerPlayerList(matchType: Int, playerDTO: PlayerDTO)
    }
}