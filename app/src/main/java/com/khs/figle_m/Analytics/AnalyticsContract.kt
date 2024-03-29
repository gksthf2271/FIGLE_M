package com.khs.figle_m.Analytics

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse

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
        fun loadPlayerList(ouid: String, matchDetailList: List<MatchDetailResponse>)
        fun loadPlayerInfoList(playerMap: Map<Int, List<PlayerDTO>>)
        fun loadPlayerImageUrl(playerInfoList: List<AnalyticsPlayer>)
        fun loadRankerPlayerList(matchType: Int, playerDTO: PlayerDTO)
    }
}